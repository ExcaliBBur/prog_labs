package com.company.exceptions;

public class IncorrectCoordinateException extends Exception{
    @Override
    public String toString(){
        return "Ошибка. Неправильная координата";

    }
}
