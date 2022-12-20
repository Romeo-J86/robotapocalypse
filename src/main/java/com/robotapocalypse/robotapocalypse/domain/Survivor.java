package com.robotapocalypse.robotapocalypse.domain;

import com.robotapocalypse.robotapocalypse.domain.embeddables.Location;
import com.robotapocalypse.robotapocalypse.util.enums.Gender;
import com.robotapocalypse.robotapocalypse.util.enums.InfectionStatus;

import javax.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 12:15
 */

@Entity
@Table(name = "survivor")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Survivor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private InfectionStatus infectionStatus;

    @Embedded
    private Location location;

    private LocalDate dateCreated;

    private int age;

    private Integer infectionReportTracker;


}
