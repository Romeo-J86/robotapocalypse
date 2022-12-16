package com.robotapocalypse.robotapocalypse.domain.embeddables;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 12:38
 */
@Data
@Embeddable
public class Location {
    private double longitude;
    private double latitude;
}
