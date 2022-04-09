package com.company.exceptions;

public class IncorrectWeightException extends Exception{
    @Override
    public String toString(){
        return "Ошибка. Вес должен быть больше нуля";
    }
}
