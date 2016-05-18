package com.starikov.dash.service;

import com.starikov.dash.entity.JiraEpic;

import java.util.List;

public interface IJiraService {

    List<JiraEpic> getJiraEpics();
    int getOpenDefectPerEpicByName(String epicName);
}
