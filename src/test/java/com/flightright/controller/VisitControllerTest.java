package com.flightright.controller;

import com.flightright.process.VisitCounterJobListener;
import com.flightright.process.VisitsCSVFileReader;
import com.flightright.scheduler.VisitsCounterScheduler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@EnableBatchProcessing
@AutoConfigureMockMvc
public class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VisitsCounterScheduler visitsCounterScheduler;

    @Test
    public void test_getVisitsCount() throws Exception {
        //given
        int expectedVisitsCount = 0;

        //when
        MvcResult response = mockMvc.perform(get("/v1/visits"))
                .andExpect(status().isOk()).andReturn();

        //then
        assertEquals(expectedVisitsCount, Integer.valueOf(response.getResponse().getContentAsString()));


        // Test after job run

        //given
        expectedVisitsCount = 5;
        visitsCounterScheduler.run();

        //when
        response = mockMvc.perform(get("/v1/visits"))
                .andExpect(status().isOk()).andReturn();

        //then
        assertEquals(expectedVisitsCount, Integer.valueOf(response.getResponse().getContentAsString()));
    }
}