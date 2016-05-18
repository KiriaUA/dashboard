package com.starikov.dash.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starikov.dash.entity.JiraEpic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JiraService implements IJiraService {
    @Value("${jira.user}")
    String userAndPass;

    @Autowired
    private ObjectMapper jsonMapper;

    @Override
    public List<JiraEpic> getJiraEpics() {
        List<JiraEpic> jiraEpics;

        String jiraResponse = getData("https://portal-jira.playtech.corp/rest/api/2/search?jql=type = Epic and component = \"Games (ASH)\"");
        jiraEpics = parseJiraEpics(jiraResponse);
        return jiraEpics;
    }

    @Override
    public int getOpenDefectPerEpicByName(String epicName) {
        String jiraKey = parseEpicJiraKey(getData("https://portal-jira.playtech.corp/rest/api/2/search?jql=type = Epic AND summary ~ '" + epicName + "'"));
        return parseOpenDefectsByEpicKey(getData("https://portal-jira.playtech.corp/rest/api/2/search?jql=status = Open AND \"Epic Link\" = " + jiraKey));
    }

    private int parseOpenDefectsByEpicKey(String jiraResponse) {
        try {
            JsonNode tree = jsonMapper.readTree(jiraResponse);
            return tree.get("issues").size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String parseEpicJiraKey(String jiraResponse) {
        try {
            JsonNode tree = jsonMapper.readTree(jiraResponse);
            return tree.get("issues").get(0).get("key").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private List<JiraEpic> parseJiraEpics(String jiraResponse) {
        List<JiraEpic> jiraEpicList = new ArrayList<>();

        try {
            JsonNode tree = jsonMapper.readTree(jiraResponse);

            JsonNode issues = tree.get("issues");
            for (JsonNode issue : issues) {
                String id = issue.get("id").asText();
                JsonNode fields = issue.get("fields");
                String summary = fields.get("summary").asText();
                String icon = fields.get("priority").get("iconUrl").asText();
                jiraEpicList.add(new JiraEpic(id, summary, icon));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jiraEpicList;
    }

    private String getData(String url) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + userAndPass);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> exchange = restTemplate.exchange(
                url,
                HttpMethod.GET, entity, String.class);

        return exchange.getBody();
    }
}
