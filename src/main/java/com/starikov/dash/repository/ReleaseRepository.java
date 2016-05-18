package com.starikov.dash.repository;

import com.starikov.dash.entity.Release;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends CrudRepository<Release, Long> {
    Release findReleaseByReleaseNumber(String releaseNumber);
    List<Release> findByOrderByReleaseNumberAsc();
}
