package com.starikov.dash.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starikov.dash.entity.*;
import com.starikov.dash.repository.EpicRepository;
import com.starikov.dash.repository.ReleaseRepository;
import com.starikov.dash.repository.UserRepository;
import com.starikov.dash.service.IJiraEpicService;
import com.starikov.dash.service.IJiraUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FileProvider implements IFileProvider {

    private static final Logger logger = Logger.getLogger(FileProvider.class);

    @Autowired
    private EpicRepository epicRepository;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IJiraUserService jiraUserService;

    @Autowired
    private IJiraEpicService jiraEpicService;

    @Autowired
    @Qualifier("jsonMapper")
    private ObjectMapper jsonMapper;

    /**
     * Folder, where epics files are stored
     */
    @Value("${dash.epic.folder}")
    String epicsFolder;

    @Value("${dash.json.folder}")
    String jsonFolder;

    @PostConstruct
    private void parseJSONFiles() throws Exception {
        Thread tUserParse = new Thread(this::parseUsersInformation);
        Thread tEpicParse = new Thread(this::parseEpicFilesFromStorage);
        Thread tReleaseParse = new Thread(this::parseReleaseInformation);
        Thread tWireData = new Thread(() -> {
            wireEpicsWithRelease();
            wireEpicsWithUsers();
            logger.info("\n===================\n" +
                    "LOADING THREADS WERE FINISHED\n" +
                    "==================="

            );
        });

        tUserParse.start();
        tEpicParse.start();
        tReleaseParse.start();

        new Thread(() -> {
            while (tUserParse.isAlive() || tEpicParse.isAlive() || tReleaseParse.isAlive()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            tWireData.start();
        }).start();
    }

    private void wireEpicsWithUsers() {
        Iterable<Epic> epics = epicRepository.findAll();
        for (Epic epic : epics) {
            epic.setDeveloper(userRepository.findByName(epic.getDeveloperName()));
            epicRepository.save(epic);
        }
    }

    private void wireEpicsWithRelease() {
        Iterable<Epic> epics = epicRepository.findAll();
        for (Epic epic : epics) {
            epic.setRelease(releaseRepository.findReleaseByReleaseNumber(epic.getReleaseName()));
            epicRepository.save(epic);
        }
    }

    @Override
    public void parseEpicFilesFromStorage() {
        logger.info("Start epics parsing");
        File folder = new File(epicsFolder);
        for (File file : folder.listFiles()) {
            Epic game = null;
            try {
                game = jsonMapper.readValue(file, Epic.class);
                EpicJiraDetails epicInfo = jiraEpicService.getGeneralEpicInfo(game.getTitle());
                game.setEpicJiraDetails(epicInfo);
                epicRepository.save(game);
                logger.info("Successfully parse " + game.getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void writeEpicToFile(Epic epic) {
        try {
            String pathToFile = epicsFolder + epic.getGameCode() + ".json";
            logger.info(pathToFile);
            File file = new File(pathToFile);
            jsonMapper.writeValue(file, epic);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void removeFileForEpic(Epic epic) {
        try {
            String pathToFile = epicsFolder + epic.getGameCode() + ".json";
            logger.info("Removing " + pathToFile);
            File file = new File(pathToFile);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void parseReleaseInformation() {
        String pathToFile = jsonFolder + "releases.json";
        logger.info("Start Release information parsing");
        File file = new File(pathToFile);
        try {
            JsonNode jsonNode = jsonMapper.readTree(file);
            for (JsonNode releaseJSON : jsonNode) {
                Release release = new Release();
                release.setReleaseNumber(releaseJSON.get("number").asText());
                release.setReleaseColor(releaseJSON.get("color").asText());
                release.setReleaseDate(LocalDate.parse(releaseJSON.get("date").asText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                releaseRepository.save(release);
            }
        } catch (IOException e) {
            logger.error("Some problem occurred during release information parsing. Check if file 'releases.json' exists");
            e.printStackTrace();
        }
    }

    @Override
    public void saveReleases() {
        String pathToFile = jsonFolder + "releases.json";
        logger.info("Start Release information saving");
        File file = new File(pathToFile);
        Iterable<Release> allReleases = releaseRepository.findAll();
        try {
            jsonMapper.writeValue(file, allReleases);
        } catch (IOException e) {
            logger.error("Some problem occurred during release information saving. Check if file 'releases.json' exists");
            e.printStackTrace();
        }
    }

    @Override
    public void parseUsersInformation() {
        String pathToFile = jsonFolder + "users.json";
        logger.info("Start Users information parsing");
        File file = new File(pathToFile);
        try {
            JsonNode tree = jsonMapper.readTree(file);
            for (JsonNode node : tree) {
                User user = new User();
                user.setName(node.get("name").asText());
                user.setLogin(node.get("login").asText());
                user.setPosition(User.Position.valueOf(node.get("position").asText()));
                user.setUserDetails(new UserJiraDetails(jiraUserService.getUserAvatar(user.getLogin()), jiraUserService.getOpenDefectsForUser(user.getLogin())));
                userRepository.save(user);
            }
        } catch (IOException e) {
            logger.error("Problem occurred during users information parsing. Check if file 'users.json' exists");
            e.printStackTrace();
        }
    }
}
