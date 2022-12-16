package com.robotapocalypse.robotapocalypse.api;

import com.robotapocalypse.robotapocalypse.service.inventory.InventoryDto;
import com.robotapocalypse.robotapocalypse.service.robot.RobotDto;
import com.robotapocalypse.robotapocalypse.service.robot.RobotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.CommunicationException;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 18:31
 */
@RestController
@RequestMapping("v1/robot/apocalypse")
@RequiredArgsConstructor
public class RobotRestController {
    private final RobotService robotService;
    @GetMapping("/robots")
    @Operation(summary = "Get Robots")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Robots found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RobotDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Robots not found",
                    content = @Content) })
    public ResponseEntity<RobotDto> getAllRobots() throws CommunicationException {
        return ResponseEntity.ok().body(robotService.listAllRobots());
    }
}
