package com.robotapocalypse.robotapocalypse.errohandling;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(String message) {
        super(message);
    }
}
