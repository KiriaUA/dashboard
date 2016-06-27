package com.starikov.dash.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.starikov.dash.entity.User;
import com.starikov.dash.repository.UserRepository;
import com.starikov.dash.service.IActivityStreamService;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class ActivityStreamService extends AbstractJiraService implements IActivityStreamService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getStream() {
        ArrayList<Object> objects = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        String stream = getData(getStreamUrl());
        JSONObject jsonObject = XML.toJSONObject(stream);
        try {
            JsonNode tree = jsonMapper.readTree(jsonObject.toString());
            JsonNode jsonNode = tree.get("feed").get("entry");
            for (JsonNode activityObject : jsonNode) {
                objects.add(activityObject);
                stringBuilder.append(activityObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return jsonMapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String getStreamUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append("https://portal-jira.playtech.corp/activity?os_authType=basic&streams=user IS");
        for (User user : userRepository.findAll()) {
            builder.append(" ").append(user.getLogin());
        }
        return builder.toString();
    }
}
