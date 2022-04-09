package com.company.utilities;

import Client.Client;
import Client.CommandSerializable;
import com.company.exceptions.IncorrectIdException;
import com.company.sourse.Color;
import com.company.sourse.Dragon;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Command controller class
 */
public class CommandHandlerClient {
    /**
     * scanner for user_mode console
     */
    Scanner scanner = new Scanner(System.in);
    public static LinkedHashMap<Long, Dragon> collection = new LinkedHashMap<>();
    private String argument;
    private String command;

    /**
     * Console user_mode
     */
    public CommandHandlerClient() throws IOException, ClassNotFoundException {
        try {
            try {
                if (DataController.flag) {
                    String[] temp = scanner.nextLine().toLowerCase(Locale.ROOT).split(" ");
                    command = temp[0];
                    argument = temp[1];
                } else {
                    String[] temp = DataController.strochka.toLowerCase(Locale.ROOT).split(" ");
                    command = temp[0];
                    System.out.println("Выполнение команды " + "\"" + DataController.strochka + "\"...");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ignored) {}
                    argument = temp[1];
                }
            }catch (ArrayIndexOutOfBoundsException ignored){};
            switch (command) {
                case ("show"):
                case ("help"):
                case ("info"):
                case ("clear"):
                case ("history"):
                case ("average_of_wingspan"):
                    Client.sendCommand(new CommandSerializable(command),1);
                    break;
                case ("add"):
                    Client.sendCommand(new CommandSerializable(command),1);
                    addCommand();
                    break;
                case ("exit"):
                    Client.sendCommand(new CommandSerializable(command),1);
                    Client.exit();
                    break;
                case ("replace_if_greater"):
                case ("replace_if_lower"):
                case ("update"):
                    try {
                        if (Integer.parseInt(argument) <= 0) throw new IncorrectIdException();
                        Client.sendCommand(new CommandSerializable(command,argument),1);
                        if (Client.receiveError().equals(true)){}
                        else {
                            addCommand();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                        new CommandHandlerClient();
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                        new CommandHandlerClient();
                    }catch (IncorrectIdException e){
                        System.err.println("Ошибка. Id должен быть больше нуля.");
                        new CommandHandlerClient();
                    }
                    break;
                case ("remove_key"):
                    try {
                        if (Integer.parseInt(argument) <= 0) throw new IncorrectIdException();
                        Client.sendCommand(new CommandSerializable(command,argument),1);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                        new CommandHandlerClient();
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                        new CommandHandlerClient();
                    }catch (IncorrectIdException e){
                        System.err.println("Ошибка. Id должен быть больше нуля.");
                        new CommandHandlerClient();
                    }
                    break;
                case ("save"):
                    System.out.println("Команда save не доступна.");
                    new CommandHandlerClient();
                    break;
                case ("insert"):
                    try {
                        if (Integer.parseInt(argument) <= 0) throw new IncorrectIdException();
                        Client.sendCommand(new CommandSerializable(command,argument),1);
                        if (Client.receiveError().equals(true)){}
                        else {
                            insertCommand();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                        new CommandHandlerClient();
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                        new CommandHandlerClient();
                    }catch (IncorrectIdException e){
                        System.err.println("Ошибка. Id должен быть больше нуля.");
                        new CommandHandlerClient();
                    }
                    break;
                case ("execute_script"):
                    try {
                        Client.sendCommand(new CommandSerializable(command,argument),1);
                        readScriptFile();
                        DataController.flag = false;
                        executeScript();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели название файла");
                        new CommandHandlerClient();
                    }
                    break;
                case ("count_less_than_color"):
                    try {
                        Color.valueOf(argument.toUpperCase(Locale.ROOT));
                        Client.sendCommand(new CommandSerializable(command,argument),1);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды");
                        new CommandHandlerClient();
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                        new CommandHandlerClient();
                    }catch (IllegalArgumentException e){
                        System.err.println("Ошибка. Данного цвета нет в списках.");
                        new CommandHandlerClient();
                    }
                    break;
                case ("filter_less_than_weight"):
                    try {
                         Client.sendCommand(new CommandSerializable(command,argument),1);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды");
                        new CommandHandlerClient();
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                        new CommandHandlerClient();
                    }
                    break;
                default:
                    System.err.println("Такой команды не найдено!Введите \"help\" для получения списка команд");
                    if (!DataController.flag){
                        try {
                            DataController.line.removeFirst();
                            DataController.strochka = DataController.line.getFirst();
                        }catch (NoSuchElementException ignored){
                            DataController.flag = true;
                            System.out.println("Переход в консольный режим...");
                        }
                    }
                    new CommandHandlerClient();
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Ошибка. Вы не ввели команду");
            if (!DataController.flag) {
                try {
                    DataController.line.removeFirst();
                    DataController.strochka = DataController.line.getFirst();
                }catch (NoSuchElementException ignored){
                    DataController.flag = true;
                    System.out.println("Переход в консольный режим...");
                }
            }
            new CommandHandlerClient();
        } catch (IOException | ClassNotFoundException e) {
        }
    }
    public void addCommand() throws IOException, ClassNotFoundException {
        DataController data = new DataController();
        Dragon dragon = new Dragon(DataController.getId(), data.getName(), data.getCoordinates(), DataController.getCreationDate(), data.getAge(), data.getWingspan(),
                data.getWeight(), data.getColor(), data.getHead());
        collection.put(dragon.getId(),dragon);
        Client.sendCommand(collection,0);
        collection.clear();
    }
    public void insertCommand() throws IOException, ClassNotFoundException {
        DataController data = new DataController("without id");
        Dragon dragon = new Dragon(DataController.getId(), data.getName(), data.getCoordinates(), DataController.getCreationDate(), data.getAge(), data.getWingspan(),
                data.getWeight(), data.getColor(), data.getHead());
        collection.put(dragon.getId(),dragon);
        Client.sendCommand(collection,0);
        collection.clear();
    }
    public void readScriptFile(){
        try {
            DataController.fis = new FileInputStream(argument);
            DataController.isr = new InputStreamReader(DataController.fis);
            Scanner scanner = new Scanner(DataController.isr);
            while (true) DataController.line.add(scanner.nextLine());
        }catch (FileNotFoundException e){
            System.err.println("Ошибка. Файл не найден");
        }catch (NoSuchElementException | NullPointerException e){
        } finally {
            try{
                DataController.fis.close();
            }catch (IOException | NullPointerException ignored){}
            try{
                DataController.isr.close();
            }catch (IOException | NullPointerException ignored){}
        }
    }
    public void executeScript() throws IOException, ClassNotFoundException {
        boolean temp = false;
        while (DataController.line.size() != 0){
            DataController.strochka = DataController.line.getFirst();
            Client.receiveAnswer();
            new CommandHandlerClient();
            try {
                DataController.line.removeFirst();
            }catch (NoSuchElementException ignored){temp = true;};
        }
        if (!temp) {
            DataController.flag = true;
            System.out.println("Переход в консольный режим...");
        }
    }
}

