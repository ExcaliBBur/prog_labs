package com.company.exceptions;

public class FileNotFoundException extends Exception{
    @Override
    public String toString(){
        return "Ошибка. Файл не найден.";
    }
}
