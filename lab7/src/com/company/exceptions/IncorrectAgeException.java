package com.company.exceptions;

public class IncorrectAgeException extends Exception{
    @Override
    public String toString(){
        return "Ошибка. Неправильный возраст";
    }
}
