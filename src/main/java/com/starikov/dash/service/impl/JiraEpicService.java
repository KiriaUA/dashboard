package com.starikov.dash.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.starikov.dash.entity.EpicJiraDetails;
import com.starikov.dash.service.IJiraEpicService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JiraEpicService extends AbstractJiraService implements IJiraEpicService {

    @Override
    public List<EpicJiraDetails> getJiraEpics() {
        List<EpicJiraDetails> epicJiraDetailses;

        String jiraResponse = getData("https://portal-jira.playtech.corp/rest/api/2/search?jql=type = Epic and component = \"Games (ASH)\"");
        epicJiraDetailses = parseJiraEpics(jiraResponse);
        return epicJiraDetailses;
    }

    @Override
    public int getOpenDefectPerEpicByName(String epicName) {
        String jiraKey = parseEpicJiraKey(getData("https://portal-jira.playtech.corp/rest/api/2/search?jql=type = Epic AND \"Epic Name\" =  \"" + epicName + "\" "));
        return parseOpenDefectsByEpicKey(getData("https://portal-jira.playtech.corp/rest/api/2/search?jql=(status = Open OR status = Reopened) AND \"Epic Link\" = " + jiraKey));
    }

    @Override
    public EpicJiraDetails getGeneralEpicInfo(String epicName) {
        String epicResponse = getData("https://portal-jira.playtech.corp/rest/api/2/search?jql=type = Epic AND \"Epic Name\" =  \"" + epicName + "\" ");
        String jiraKey = parseEpicJiraKey(epicResponse);
        String data = getData("https://portal-jira.playtech.corp/rest/api/2/search?jql=(status = Open OR status = Reopened) AND \"Epic Link\" = " + jiraKey);
        int openDefects = parseOpenDefectsByEpicKey(data);
        String iconUrl = parsePriorityIconUrl(epicResponse);
        return new EpicJiraDetails(jiraKey, epicName, iconUrl, openDefects);
    }

    private String parsePriorityIconUrl(String jiraResponse) {
        try {
            JsonNode tree = jsonMapper.readTree(jiraResponse);
            JsonNode epicData = tree.get("issues").get(0);
            return epicData.get("fields").get("priority").get("iconUrl").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int parseOpenDefectsByEpicKey(String jiraResponse) {
        int issuesCount = 0;
        try {
            JsonNode tree = jsonMapper.readTree(jiraResponse);
            for (JsonNode issue : tree.get("issues")) {
                String status = issue.get("fields").get("status").get("name").asText();
                if (status.equals("Open") || status.equals("Reopened"))
                    issuesCount++;
            }
            return issuesCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return issuesCount;
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

    private List<EpicJiraDetails> parseJiraEpics(String jiraResponse) {
        List<EpicJiraDetails> epicJiraDetailsList = new ArrayList<>();

        try {
            JsonNode tree = jsonMapper.readTree(jiraResponse);

            JsonNode issues = tree.get("issues");
            for (JsonNode issue : issues) {
                String id = issue.get("id").asText();
                JsonNode fields = issue.get("fields");
                String summary = fields.get("summary").asText();
                String icon = fields.get("priority").get("iconUrl").asText();
                EpicJiraDetails details = new EpicJiraDetails(id, summary, icon, 0);
                epicJiraDetailsList.add(details);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return epicJiraDetailsList;
    }
}
