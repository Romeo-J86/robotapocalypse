package com.robotapocalypse.robotapocalypse.service.robot.impl;

import com.robotapocalypse.robotapocalypse.service.robot.Robot;
import com.robotapocalypse.robotapocalypse.service.robot.RobotDto;
import com.robotapocalypse.robotapocalypse.service.robot.RobotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.robotapocalypse.robotapocalypse.util.Constants.ROBOTS_URL;

/**
 * @author Romeo Jerenyama
 * @created 16/12/2022 - 10:10
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RobotServiceImpl implements RobotService {

    private final WebClient webClient;
    @Override
    public RobotDto listAllRobots(){

        Robot[] robotsArray = webClient.get()
                .uri(ROBOTS_URL)
                .retrieve()
                .bodyToMono(Robot[].class)
                .block();

        List<Robot> flyingRobots = Arrays.stream(robotsArray)
                .filter(robot -> robot.getCategory().equalsIgnoreCase("Flying"))
                .collect(Collectors.toList());

        List<Robot> landRobots = Arrays.stream(robotsArray)
                .filter(robot -> robot.getCategory().equalsIgnoreCase("Land"))
                .collect(Collectors.toList());

        RobotDto robotDto = RobotDto.builder()
                .numberOfFlyingRobots(flyingRobots.stream().count())
                .numberOfLandRobots(landRobots.stream().count())
                .flyingRobots(flyingRobots)
                .landRobots(landRobots)
                .build();
        return robotDto;
    }
}
