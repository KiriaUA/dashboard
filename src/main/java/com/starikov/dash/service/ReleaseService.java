package com.starikov.dash.service;

import com.starikov.dash.entity.Release;
import com.starikov.dash.repository.ReleaseRepository;
import com.starikov.dash.util.IFileProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseService implements IReleaseService {

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private IFileProvider fileProvider;

    @Override
    public Release getReleaseByName(String releaseName) {
        return releaseRepository.findReleaseByReleaseNumber(releaseName);
    }

    @Override
    public List<Release> getAllReleases() {
        return releaseRepository.findByOrderByReleaseNumberAsc();
    }

    @Override
    public void saveRelease(Release release) {
        releaseRepository.save(release);
        fileProvider.saveReleases();
    }
}
