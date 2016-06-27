package com.starikov.dash.service;

import com.starikov.dash.entity.EpicJiraDetails;

import java.util.List;
import java.util.Map;

public interface IJiraEpicService {
    EpicJiraDetails getGeneralEpicInfo(String epicName);
    List<EpicJiraDetails> getJiraEpics();
    int getOpenDefectPerEpicByName(String epicName);
}
