package com.flightright.repository;

import com.flightright.entity.VisitCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<VisitCount, Integer> {
    Optional<VisitCount> findFirstByOrderByIdAsc();
//    VisitCount findTopBy

    //findFirstByVinOrderByCreatedTimestampDesc
}
