package com.robotapocalypse.robotapocalypse.service.survivor;

import com.robotapocalypse.robotapocalypse.domain.Survivor;
import com.robotapocalypse.robotapocalypse.domain.embeddables.Location;
import com.robotapocalypse.robotapocalypse.util.enums.Gender;
import com.robotapocalypse.robotapocalypse.util.enums.InfectionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 16:07
 */

@Data
public class SurvivorDto {
    private Long id;
    private String firstName;
    private  String lastName;
    private Gender gender;
    private InfectionStatus infectionStatus;
    private Location location;
    private int age;
    private int infectionReportTracker;
    private LocalDateTime dateCreated;



}
