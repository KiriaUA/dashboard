package com.starikov.dash.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starikov.dash.entity.Epic;
import com.starikov.dash.entity.Release;
import com.starikov.dash.repository.EpicRepository;
import com.starikov.dash.repository.ReleaseRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class EpicsFileProvider implements IFileProvider {

    private static final Logger logger = Logger.getLogger(EpicsFileProvider.class);

    @Autowired
    private EpicRepository epicRepository;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private ObjectMapper jsonMapper;

    /**
     * Folder, where epics files are stored
     */
    @Value("${dash.epic.folder}")
    String epicsFolder;

    @Value("${dash.json.folder}")
    String jsonFolder;

    @PostConstruct
    private void parseJSONFiles() throws IOException {
        parseEpicFilesFromStorage();
        parseReleaseInformation();
        wireEpicsWithRelease();
    }

    private void wireEpicsWithRelease() {
        Iterable<Epic> epics = epicRepository.findAll();
        for (Epic epic : epics) {
            epic.setRelease(releaseRepository.findReleaseByReleaseNumber(epic.getReleaseName()));
            epicRepository.save(epic);
        }
    }


    @Override
    public void parseEpicFilesFromStorage() throws IOException {
        List<Epic> list = new ArrayList<>();
        logger.info("Start epics parsing");
        File folder = new File(epicsFolder);
        for (File file : folder.listFiles()) {
            Epic game = jsonMapper.readValue(file, Epic.class);
            list.add(game);
            epicRepository.save(game);
            logger.info("Successfully parse " + game.getTitle());
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
}
