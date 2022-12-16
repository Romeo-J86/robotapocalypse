package com.robotapocalypse.robotapocalypse.service.infectionrecord;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 21:06
 */
@Data
public class InfectionRecordRequest {
    private Long reportedById;
    private Long survivorReportedId;
    private LocalDateTime dateReported;

}
