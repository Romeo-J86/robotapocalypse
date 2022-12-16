package com.robotapocalypse.robotapocalypse.errohandling;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 12:27
 */
public class SurvivorNotFoundException extends RuntimeException{
    public SurvivorNotFoundException(String message) {
        super(message);
    }
}
