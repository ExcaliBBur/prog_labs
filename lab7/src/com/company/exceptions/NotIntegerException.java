package com.company.exceptions;

public class NotIntegerException extends Exception{
    @Override
    public String toString(){
        return "Ошибка. Необходимо ввести ЦЕЛОЕ число.";
    }
}
