package com.endava.batch.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.endava.batch.controller.hateoas.HateoasResourceAssembler;
import com.endava.batch.request.CalculationRequest;
import com.endava.batch.service.FactorialService;
import com.endava.batch.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest
public class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JobService jobService;

    @MockBean
    private FactorialService factorialService;

    @SpyBean
    private HateoasResourceAssembler hateoasResourceAssembler;

    @Before
    public void setUp() {
        when(jobService.startJob(any())).thenReturn(1L);
    }

    @Test
    public void test() throws Exception {
        final CalculationRequest request = new CalculationRequest(5);
        mockMvc.perform(post("/factorials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(202))
                .andExpect(header().string("Location", endsWith("factorials/1")))
                .andExpect(jsonPath("_links.jobResult").exists())
                .andExpect(jsonPath("_links.jobState").exists());
    }
}