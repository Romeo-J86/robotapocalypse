package com.robotapocalypse.robotapocalypse.service.infectionrecord;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 21:36
 */
@Data
public class InfectionRecordDto {
    private Long id;
    private String reportedBy;
    private Long reportedById;
    private String survivorReported;
    private Long survivorReportedId;
    private LocalDateTime dateReported;

}
