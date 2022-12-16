package com.robotapocalypse.robotapocalypse.service.survivor;

import com.robotapocalypse.robotapocalypse.domain.embeddables.Location;
import com.robotapocalypse.robotapocalypse.util.enums.InfectionStatus;

import java.util.List;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 12:35
 */
public interface SurvivorService {

    /**
     * This method creates/saves Survivor from SurvivorSaveRequest
     * @param survivorSaveRequest
     * @return SurvivorDto
     */
    SurvivorDto saveSurvivor(SurvivorSaveRequest survivorSaveRequest);

    /**
     * This method updates the Survivor Location
     * @param survivorId unique identifier of Survivor
     * @param location location to update the Survivor with
     * @return SurvivorDto
     */
    SurvivorDto updateLocation(Long survivorId,Location location);

    /**
     * This method fetches survivors by infection status which can
     * either be INFECTED or NON_INFECTED
     * @param infectionStatus
     * @return List<SurvivorDto>
     */
    List<SurvivorDto> getSurvivorsByInfectionStatus(InfectionStatus infectionStatus);

    /**
     * This method calculates percentage of infected or non_infected survivors
     * @param infectedStatus
     * @return Double
     */
    Double getInfectedOrNonInfectedPercentage(InfectionStatus infectedStatus);

}
