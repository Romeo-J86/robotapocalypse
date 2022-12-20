package com.robotapocalypse.robotapocalypse.util.enums;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 15:55
 */
public enum InventoryType {

    /**
     * Food type with kilograms unit of measure
     */
    FOOD("food","kg"),

    /**
     * WATER type with litres unit of measure
     */
    WATER("water","ltrs"),

    /**
     * AMMUNITION type with single unit of measure
     */
    AMMUNITION("ammunition","single");

    private final String inventoryType;
    private final String unitOfMeasure;

    InventoryType(String inventoryType, String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        this.inventoryType=inventoryType;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public String getInventoryType() {
        return inventoryType;
    }
}
