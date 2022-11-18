package com.flightright.process;

import com.flightright.dto.VisitorDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class VisitsCounterWriter {
    private Set<VisitorDto> userVisitSet = new HashSet();

    public synchronized void count(VisitorDto userVisit) {
        if (!userVisitSet.contains(userVisit)) {
            userVisitSet.add(userVisit);
        }
    }

    public void clearUserVisitSet() {
        System.out.println("Call: clearUserVisitSet");
        userVisitSet.clear();
    }

    public int getUserVisitsCount() {
        return userVisitSet.size();
    }
}
