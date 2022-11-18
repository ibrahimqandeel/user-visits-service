package com.flightright.service;

import com.flightright.entity.VisitCount;

import java.util.Optional;

public interface VisitService {
    int getVisitsCount();
    void save(VisitCount visitCount);

    Optional<VisitCount> findFirstVisitCount();
}
