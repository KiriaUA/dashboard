package com.starikov.dash.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.starikov.dash.service.IJiraUserService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("jiraUserService")
public class JiraUserService extends AbstractJiraService implements IJiraUserService {

    @Override
    public String getUserAvatar(String userLogin) {
        String data = getData("https://portal-jira.playtech.corp/rest/api/2/user?username=" + userLogin);
        return parseAvatarUrl(data);
    }

    @Override
    public int getOpenDefectsForUser(String userLogin) {
        //todo: implement
        return 0;
    }

    private String parseAvatarUrl(String data) {
        String avatarUrl = "";
        try {
            JsonNode tree = jsonMapper.readTree(data);
            avatarUrl = tree.get("avatarUrls").get("32x32").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return avatarUrl;
    }
}
