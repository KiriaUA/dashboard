package com.starikov.dash.util;

import com.starikov.dash.entity.Epic;

import java.io.IOException;

public interface IFileProvider {
    void parseEpicFilesFromStorage() throws IOException;
    void writeEpicToFile(Epic epic);
    void removeFileForEpic(Epic epic);
    void parseReleaseInformation();
    void saveReleases();
}
