package com.robotapocalypse.robotapocalypse.api;

import com.robotapocalypse.robotapocalypse.domain.embeddables.Location;
import com.robotapocalypse.robotapocalypse.service.inventory.InventoryDto;
import com.robotapocalypse.robotapocalypse.service.survivor.SurvivorDto;
import com.robotapocalypse.robotapocalypse.service.survivor.SurvivorSaveRequest;
import com.robotapocalypse.robotapocalypse.service.survivor.SurvivorService;
import com.robotapocalypse.robotapocalypse.util.enums.InfectionStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 13:15
 */
@RestController
@RequestMapping("v1/robot/apocalypse/survivor")
@RequiredArgsConstructor
public class SurvivorRestController {
    private final SurvivorService survivorService;

    @Operation(summary = "Add Survivor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Survivor saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SurvivorDto.class)) })})
    @PostMapping("/save")
    public ResponseEntity<SurvivorDto> saveSurvivor(@RequestBody SurvivorSaveRequest survivorSaveRequest){
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(survivorService.saveSurvivor(survivorSaveRequest));
    }

    @Operation(summary = "Updating Survivor Location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SurvivorDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Survivor not found",
                    content = @Content) })
    @PutMapping("/update/{survivorId}")
    public ResponseEntity<SurvivorDto>  updateLocation(@PathVariable Long survivorId,
                                                       @RequestBody Location location){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(survivorService.updateLocation(survivorId,location));
    }
    @GetMapping("/report-infection/{infectionStatus}")
    @Operation(summary = "Get Survivors By Infection Status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Survivors found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SurvivorDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Survivors not found",
                    content = @Content) })
    public ResponseEntity<List<SurvivorDto>> getSurvivorsByInfectionStatus(@PathVariable InfectionStatus infectionStatus){
        return ResponseEntity.ok(survivorService.getSurvivorsByInfectionStatus(infectionStatus));
    }
}
