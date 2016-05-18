package com.starikov.dash.service;

import com.starikov.dash.entity.Epic;
import com.starikov.dash.repository.EpicRepository;
import com.starikov.dash.util.IFileProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpicService implements IEpicService {

    @Autowired
    private EpicRepository epicRepository;

    @Autowired
    private IFileProvider epicFileProvider;

    @Override
    public Epic getEpicByGameCode(String code) {
        return null;
    }

    @Override
    public List<Epic> getEpics() {
        return epicRepository.findAllByOrderByRelease_ReleaseDateAsc();
    }

    @Override
    public void addEpic(Epic epic) {
        epicFileProvider.writeEpicToFile(epic);
        epicRepository.save(epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        epicRepository.delete(epic);
        addEpic(epic);
    }

    @Override
    public void removeEpicById(long id) {
        Epic epic = epicRepository.findOne(id);
        epicFileProvider.removeFileForEpic(epic);
        epicRepository.delete(epic);
    }
}
