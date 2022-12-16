package com.robotapocalypse.robotapocalypse.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 20:56
 */
@Entity
@Table(name = "infection_record")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InfectionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reportedBy;
    private Long reportedById;
    private String survivorReported;
    private Long survivorReportedId;
    private LocalDateTime dateReported;
}
