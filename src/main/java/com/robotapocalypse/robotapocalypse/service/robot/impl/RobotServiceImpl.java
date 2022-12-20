package com.robotapocalypse.robotapocalypse.service.robot.impl;

import com.robotapocalypse.robotapocalypse.errohandling.RobotNotFoundException;
import com.robotapocalypse.robotapocalypse.service.robot.Robot;
import com.robotapocalypse.robotapocalypse.service.robot.RobotDto;
import com.robotapocalypse.robotapocalypse.service.robot.RobotService;
import com.robotapocalypse.robotapocalypse.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.CommunicationException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * @author Romeo Jerenyama
 * @created 16/12/2022 - 10:10
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RobotServiceImpl implements RobotService {

    private final RestTemplate restTemplate;
    @Override
    public RobotDto listAllRobots() throws CommunicationException {

        ResponseEntity<Robot[]> robotsResponse = restTemplate.getForEntity(Constants.ROBOTS_URL, Robot[].class);

        if(robotsResponse.getStatusCode() != HttpStatus.OK){
            throw new CommunicationException("Could not reach the robot service");
        }

        Robot[] robotsArray = robotsResponse.getBody();
        if(isNull(robotsArray))
            throw new RobotNotFoundException("No Robots found");


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
