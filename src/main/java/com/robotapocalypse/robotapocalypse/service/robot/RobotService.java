package com.robotapocalypse.robotapocalypse.service.robot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.CommunicationException;

/**
 * @author Romeo Jerenyama
 * @created 16/12/2022 - 10:07
 */
public interface RobotService {

    /**
     * retrieves a list of robots
     * @return
     */
    RobotDto listAllRobots();

}
