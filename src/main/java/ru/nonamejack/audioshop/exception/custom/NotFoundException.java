package ru.nonamejack.audioshop.exception.custom;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
