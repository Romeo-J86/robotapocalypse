package com.robotapocalypse.robotapocalypse.errohandling;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 12:27
 */
public class InventoryNotFoundException extends RuntimeException{
    public InventoryNotFoundException(String message) {
        super(message);
    }
}
