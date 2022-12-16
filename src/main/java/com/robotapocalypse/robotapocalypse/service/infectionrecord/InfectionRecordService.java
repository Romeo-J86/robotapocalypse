package com.robotapocalypse.robotapocalypse.service.infectionrecord;

import com.robotapocalypse.robotapocalypse.domain.InfectionRecord;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 21:05
 */
public interface InfectionRecordService {
    /**
     * This method report infection of survivor using InfectionRecordRequest
     * @param infectionRecordRequest
     * @return InfectionRecordDto
     */
    InfectionRecordDto reportInfection(InfectionRecordRequest infectionRecordRequest);
}
