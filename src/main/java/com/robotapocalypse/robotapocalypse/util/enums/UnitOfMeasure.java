package com.robotapocalypse.robotapocalypse.util.enums;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 15:55
 */
public enum UnitOfMeasure {
    LITRES("ltr"),
    SINGLE("sn"),
    KILOGRAMS("kgs");

    private final String unitOfMeasure;
    UnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }
}
