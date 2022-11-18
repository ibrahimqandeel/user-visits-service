package com.flightright.controller;

import com.flightright.process.VisitsCounterWriter;
import com.flightright.service.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/visits")
@AllArgsConstructor
public class VisitController {

    private final VisitService visitService;
    private final VisitsCounterWriter visitsCounterWriter;

    @GetMapping
    public Integer getVisitsCount() {
        return visitService.getVisitsCount();
    }

    @GetMapping("test")
    public Integer getVisitsCountTest() {
        return visitsCounterWriter.getUserVisitsCount();
    }
}
