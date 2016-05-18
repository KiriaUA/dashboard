package com.starikov.dash.service;

import com.starikov.dash.entity.Epic;

import java.util.List;

public interface IEpicService {
    Epic getEpicByGameCode(String code);
    List<Epic> getEpics();
    void addEpic(Epic epic);
    void updateEpic(Epic epic);
    void removeEpicById(long id);
}
