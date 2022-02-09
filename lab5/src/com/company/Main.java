package com.company;

import com.company.utilities.CommandController;
import com.company.utilities.FileController;

import java.util.TreeMap;


public class Main {
    public static void main(String[] args) {
        FileController file = new FileController();
        file.readCollection();
        while (true){
            new CommandController();
            System.out.print("$ ");
        }
    }
}
