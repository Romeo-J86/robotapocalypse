package com.robotapocalypse.robotapocalypse.errohandling;

/**
 * @author Romeo Jerenyama
 * @created 16/12/2022 - 10:22
 */
public class RobotNotFoundException  extends RuntimeException{
    public RobotNotFoundException(String message) {
        super(message);
    }
}
