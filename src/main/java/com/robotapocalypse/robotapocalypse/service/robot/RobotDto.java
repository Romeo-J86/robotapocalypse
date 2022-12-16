package com.robotapocalypse.robotapocalypse.service.robot;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Romeo Jerenyama
 * @created 16/12/2022 - 09:58
 */
@Data
@Builder
public class RobotDto {
    Long numberOfLandRobots;
    Long numberOfFlyingRobots;
    List<Robot> flyingRobots;
    List<Robot> landRobots;
}
