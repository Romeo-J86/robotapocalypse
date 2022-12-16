package com.robotapocalypse.robotapocalypse.service.survivor;

import com.robotapocalypse.robotapocalypse.domain.embeddables.Location;
import lombok.Builder;
import lombok.Data;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 12:35
 */
@Data
public class SurvivorSaveRequest {
    private String firstName;
    private  String lastName;
    private String gender;
    private Location location;
    private int age;

}
