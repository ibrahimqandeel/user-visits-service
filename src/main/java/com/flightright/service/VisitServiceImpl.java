package com.flightright.service;

import com.flightright.entity.VisitCount;
import com.flightright.repository.VisitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    @Override
    public int getVisitsCount() {
        Optional<VisitCount> visitCount = visitRepository.findFirstByOrderByIdAsc();
        return visitCount.isPresent() ? visitCount.get().getCount() : 0;
    }

    @Override
    public void save(VisitCount visitCount) {
        visitRepository.save(visitCount);
    }

    @Override
    public Optional<VisitCount> findFirstVisitCount() {
        return visitRepository.findFirstByOrderByIdAsc();
    }
}