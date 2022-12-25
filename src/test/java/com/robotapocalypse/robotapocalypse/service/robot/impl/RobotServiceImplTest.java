package com.robotapocalypse.robotapocalypse.service.robot.impl;

import com.robotapocalypse.robotapocalypse.service.robot.RobotDto;
import com.robotapocalypse.robotapocalypse.service.robot.RobotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

/**
 * @author Romeo Jerenyama
 * @created 21/12/2022 - 23:49
 */
@ExtendWith(MockitoExtension.class)
class RobotServiceImplTest {

    @MockBean
    private RobotService robotService;
    @Autowired
    private WebClient webClient;


    @Test
    void listAllRobots() {
        //GIVEN
        RobotDto robotDto = RobotDto.builder().build();
        //WHEN
        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriMock.uri("/employee/{id}", employeeId))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseMock.bodyToMono(Employee.class))
                .thenReturn(Mono.just(mockEmployee));

        when((Publisher<?>) robotService.listAllRobots()).thenReturn(robotDto);
    }
}