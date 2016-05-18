package com.starikov.dash.service;

import com.starikov.dash.entity.Release;

import java.util.List;

public interface IReleaseService {
    Release getReleaseByName(String releaseName);
    List<Release> getAllReleases();
    void saveRelease(Release release);
}
