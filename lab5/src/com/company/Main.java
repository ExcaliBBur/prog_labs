package com.company;

import com.company.utilities.CommandController;
import com.company.utilities.FileController;

import java.net.ServerSocket;
import java.time.LocalDate;
import java.time.LocalDateTime;

//в блоке файнали надо сделать закрытие файлов
//регулярка для даты
public class Main {
    public static void main(String[] args) {
        FileController file = new FileController();
        file.readCollection();
        while (true){
            CommandController controller = new CommandController();
            System.out.print("$ ");
        }
    }
}
