package com.company.exceptions;

public class IncorrectNameException extends Exception{
    @Override
    public String toString(){
        return "Ошибка. Неправильно имя";
    }
}
