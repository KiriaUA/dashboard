package com.starikov.dash.repository;

import com.starikov.dash.entity.Epic;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EpicRepository extends PagingAndSortingRepository<Epic, Long> {
    /**
     * Find all epics and sort them by release in ascending order
     */
    List<Epic> findAllByOrderByRelease_ReleaseDateAsc();
}