package com.starikov.dash.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starikov.dash.service.IJiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jira")
public class JiraController {

    @Autowired
    private IJiraService jiraService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(path = "epics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEpicsPerTeam() throws JsonProcessingException {
        return objectMapper.writeValueAsString(jiraService.getJiraEpics());
    }

    @RequestMapping(path = "issues/{epicName}")
    public String getJiraIssuesPerEpic(@PathVariable("epicName") String epicName) {
        return String.valueOf(jiraService.getOpenDefectPerEpicByName(epicName));
    }
}
