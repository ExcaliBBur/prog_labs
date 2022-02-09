package com.company.utilities;

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
public class CommandController {
    /**
     * scanner for user_mode console
     */
    Scanner scanner = new Scanner(System.in);
    private long weight;
    private int color;
    private long id;

    /**
     * Console user_mode
     */
    public CommandController(){
        try {
            String[] command = scanner.nextLine().toLowerCase(Locale.ROOT).split(" ");
            for (int i = 13; i > 0; i--) DataController.temp[i] = DataController.temp[i - 1];
            DataController.temp[0] = command[0];
            switch (command[0]) {
                case ("show"):
                    showCommand();
                    break;
                case ("exit"):
                    System.out.println("Сохранить коллекцию перед выходом? (Да/Нет)");
                    String exit = scanner.nextLine().toLowerCase(Locale.ROOT);
                    if (exit.equals("да")) saveCommand();
                    System.out.println("Завершение работы программы...");
                    exitCommand();
                    break;
                case ("help"):
                    helpCommand();
                    break;
                case ("info"):
                    infoCommand();
                    break;
                case ("insert"):
                    try {
                        if (FileController.getCollection().get(Long.parseLong(command[1])) != null) {
                            System.out.println("Элемент с таким айди уже есть. Хотите заменить его?(Да/Нет)");
                            String temp = scanner.nextLine().toLowerCase(Locale.ROOT);
                            if (temp.equals("да")) {
                                new CollectionController(command[1]);
                                System.out.println("Успешно удалено!");
                            } else if (temp.equals("нет")) break;
                            else System.out.println("Такой ответ не предусмотрен большим братом");
                        } else new CollectionController(command[1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                    }
                    break;
                case ("update"):
                    try {
                        if (FileController.getCollection().get(Long.parseLong(command[1])) != null) {
                            new CollectionController(command[1]);
                        } else System.out.println("Такого айди нет в коллекции.");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                    }
                    break;
                case ("remove_key"):
                    try {
                        if (FileController.getCollection().get(Long.parseLong(command[1])) != null) {
                            CollectionController.collection.remove(Long.parseLong(command[1]));
                            System.out.println("Коллекция успешно удалена");
                            //Поздравляю, вы нашли пасхалку!!!
                        } else System.out.println("Удалять нечего, ведь такого айди нет в коллекции...");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                    }
                    break;
                case ("clear"):
                    clearCommand();
                    break;
                case ("save"):
                    saveCommand();
                    break;
                case ("execute_script"):
                    try {
                        DataController.fileName = command[1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели название файла");
                    }
                    readScriptFile();
                    new CommandController("execute_script");
                    break;
                case ("history"):
                    historyCommand();
                    break;
                case ("replace_if_greater"):
                    try {
                        id = Long.parseLong(command[1]);
                        replaceGreaterCommand();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                    }
                    break;
                case ("replace_if_lower"):
                    try {
                        id = Long.parseLong(command[1]);
                        replaceLowerCommand();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                    }
                    break;
                case ("average_of_wingspan"):
                    avgWingspanCommand();
                    break;
                case ("count_less_than_color"):
                    try {
                        switch (command[1].toUpperCase(Locale.ROOT)) {
                            case ("RED"):
                                color = 1;
                                break;
                            case ("BLACK"):
                                color = 2;
                                break;
                            case ("ORANGE"):
                                color = 3;
                                break;
                            case ("BROWN"):
                                color = 4;
                                break;
                            default:
                                System.out.println("Ошибка в аргументе. Такого цвета нет в списке");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды");
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                    }
                    colorCommand();
                    break;
                case ("filter_less_than_weight"):
                    try {
                        weight = Long.parseLong(command[1]);
                        filterWeight();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды");
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                    }
                    break;
                case ("add"):
                    new CollectionController();
                    break;
                default:
                    System.err.println("Такой команды не найдено!Введите \"help\" для получения списка команд");
                    DataController.temp[0] = null;
                    for (int i = 0; i <= 12; i++) DataController.temp[i] = DataController.temp[i + 1];
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Ошибка. Вы не ввели команду");
        }
    }

    /**
     * Console script_mode
     * @param script flag that`s script_mode
     */
    public CommandController(String script) {
        try{
            while (DataController.line.size() != 0) {
                String[] command = DataController.line.getFirst().toLowerCase(Locale.ROOT).split(" ");
                if (command.length == 1) System.out.println("Исполняю команду: " + command[0] + "...");
                else System.out.println("Исполняю команду: " + command[0] + " с аргументом " + command[1] + "...");
                TimeUnit.MILLISECONDS.sleep(1000);
                for (int i = 13; i > 0; i--) DataController.temp[i] = DataController.temp[i - 1];
                DataController.temp[0] = command[0];
                switch (command[0]) {
                    case ("show"):
                        showCommand();
                        DataController.line.removeFirst();
                        break;
                    case ("exit"):
                        DataController.line.removeFirst();
                        System.out.println("Сохранить коллекцию перед выходом? (Да/Нет)");
                        String[] exit = DataController.line.getFirst().toLowerCase(Locale.ROOT).split(" ");
                        System.out.println(exit[0]);
                        if (exit[0].equals("да")) saveCommand();
                        System.out.println("Завершение работы программы...");
                        DataController.line.removeFirst();
                        exitCommand();
                        break;
                    case ("help"):
                        helpCommand();
                        DataController.line.removeFirst();
                        break;
                    case ("info"):
                        DataController.line.removeFirst();
                        infoCommand();
                        break;
                    case ("insert"):
                        try {
                            DataController.line.removeFirst();
                            if (FileController.getCollection().get(Long.parseLong(command[1])) != null) {
                                System.out.println("Элемент с таким айди уже есть. Хотите заменить его?(Да/Нет)");
                                String[] insert = DataController.line.getFirst().toLowerCase(Locale.ROOT).split(" ");
                                System.out.println(insert[0]);
                                DataController.line.removeFirst();
                                if (insert[0].equals("да")) {
                                    new CollectionController(command[1]);
                                    System.out.println("Успешно заменено!");
                                } else if (insert[0].equals("нет")) {
                                    break;
                                } else {
                                    System.out.println("Такой ответ не предусмотрен большим братом");
                                }
                            } else new CollectionController(command[1]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.err.println("Ошибка. Вы не ввели аргумент команды.");
                            DataController.line.removeFirst();
                        } catch (NumberFormatException e) {
                            System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                            DataController.line.removeFirst();
                        }
                        break;
                    case ("update"):
                        try {
                            DataController.line.removeFirst();
                            if (FileController.getCollection().get(Long.parseLong(command[1])) != null) {
                                new CollectionController(command[1]);
                            } else System.out.println("Такого айди нет в коллекции.");
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.err.println("Ошибка. Вы не ввели аргумент команды.");
                            DataController.line.removeFirst();
                        } catch (NumberFormatException e) {
                            System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                            DataController.line.removeFirst();
                        }
                        break;
                    case ("remove_key"):
                        DataController.line.removeFirst();
                        try {
                            if (FileController.getCollection().get(Long.parseLong(command[1])) != null) {
                                CollectionController.collection.remove(Long.parseLong(command[1]));
                                System.out.println("Коллекция "+command[1]+" успешно удалена");
                            } else System.out.println("Удалять нечего, ведь такого айди нет в коллекции...");
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.err.println("Ошибка. Вы не ввели аргумент команды.");
                            DataController.line.removeFirst();
                        } catch (NumberFormatException e) {
                            System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                            DataController.line.removeFirst();
                        }
                        break;
                    case ("clear"):
                        clearCommand();
                        DataController.line.removeFirst();
                        break;
                    case ("save"):
                        saveCommand();
                        DataController.line.removeFirst();
                        break;
                    case ("execute_script"):
                        System.out.println("Нельзя вызывать скрипт в скрипте");
                        DataController.line.removeFirst();
                        break;
                    case ("history"):
                        historyCommand();
                        DataController.line.removeFirst();
                        break;
                    case ("replace_if_greater"):
                        try {
                            DataController.line.removeFirst();
                            id = Long.parseLong(command[1]);
                            replaceGreaterCommand();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.err.println("Ошибка. Вы не ввели аргумент команды.");
                            DataController.line.removeFirst();
                        } catch (NumberFormatException e) {
                            System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                            DataController.line.removeFirst();
                        }
                        break;
                    case ("replace_if_lower"):
                        try {
                            DataController.line.removeFirst();
                            id = Long.parseLong(command[1]);
                            replaceLowerCommand();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.err.println("Ошибка. Вы не ввели аргумент команды.");
                            DataController.line.removeFirst();
                        } catch (NumberFormatException e) {
                            System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                            DataController.line.removeFirst();
                        }
                        break;
                    case ("average_of_wingspan"):
                        avgWingspanCommand();
                        DataController.line.removeFirst();
                        break;
                    case ("count_less_than_color"):
                        try {
                            switch (command[1].toUpperCase(Locale.ROOT)) {
                                case ("RED"):
                                    color = 1;
                                    break;
                                case ("BLACK"):
                                    color = 2;
                                    break;
                                case ("ORANGE"):
                                    color = 3;
                                    break;
                                case ("BROWN"):
                                    color = 4;
                                    break;
                                default:
                                    System.out.println("Ошибка в аргументе. Такого цвета нет в списке");
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.err.println("Ошибка. Вы не ввели аргумент команды");
                            DataController.line.removeFirst();
                        } catch (NumberFormatException e) {
                            System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                            DataController.line.removeFirst();
                        }
                        colorCommand();
                        DataController.line.removeFirst();
                        break;
                    case ("filter_less_than_weight"):
                        try {
                            weight = Long.parseLong(command[1]);
                            DataController.line.removeFirst();
                            filterWeight();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.err.println("Ошибка. Вы не ввели аргумент команды");
                            DataController.line.removeFirst();
                        } catch (NumberFormatException e) {
                            System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                            DataController.line.removeFirst();
                        }
                        break;
                    case ("add"):
                        DataController.line.removeFirst();
                        new CollectionController();
                        break;
                    default:
                        DataController.line.removeFirst();
                        System.err.println("Такой команды не найдено!Введите \"help\" для получения списка команд");
                        DataController.temp[0] = null;
                        for (int i = 0; i <= 12; i++) DataController.temp[i] = DataController.temp[i + 1];
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            System.out.println("Исполнять больше нечего!");
        }catch (InterruptedException e){}
    }

    /**
     * Method to show collection
     */
    public void showCommand(){
        CollectionController.getCollectionForUser();
    }

    /**
     * Method to exit programm
     */
    public void exitCommand(){
        System.exit(0);
    }

    /**
     * Method to clear collection from file
     */
    public void clearCommand(){
        CollectionController.collection.clear();
        FileController file = new FileController();
        file.writeSpace();
        System.out.println("Коллекция успешно очищена");
    }

    /**
     * Method to get collection info
     */
    public void infoCommand(){
        System.out.println("Инициализация: " + FileController.lastInit);
        System.out.println("Тип коллекции: " + FileController.collection.getClass().getName());
        System.out.println("Количество элементов в коллекции: "+FileController.collection.size());
    }

    /**
     * Method to save collection to file
     */
    public void saveCommand(){
        FileController file = new FileController();
        file.writeCollection();
    }

    /**
     * Method to get description for all commands
     */
    public static void helpCommand(){
        System.out.printf("%-50s%-1s%n","info: ","вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.) ");
        System.out.printf("%-50s%-1s%n", "show: ","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        System.out.printf("%-50s%-1s%n","insert {id}: ", "добавить новый элемент с заданным ключом");
        System.out.printf("%-50s%-1s%n","update {id}: ","обновить значение элемента коллекции, id которого равен заданному");
        System.out.printf("%-50s%-1s%n","remove_key {id}: ","удалить элемент из коллекции по его ключу");
        System.out.printf("%-50s%-1s%n","add: ","добавить новый элемент ");
        System.out.printf("%-50s%-1s%n","clear: ","очистить коллекцию(сразу из файла)");
        System.out.printf("%-50s%-1s%n","save: ", "сохранить коллекцию в файл");
        System.out.printf("%-50s%-1s%n","execute_script {file_name}(абсолютный):","считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.printf("%-50s%-1s%n","exit: ","завершить программу (без сохранения в файл)");
        System.out.printf("%-50s%-1s%n","history: ", "вывести последние 14 команд (без их аргументов)");
        System.out.printf("%-50s%-1s%n","replace_if_greater {id}: ","заменить значение по ключу, если новое значение больше старого");
        System.out.printf("%-50s%-1s%n","replace_if_lower {id}: ","заменить значение по ключу, если новое значение меньше старого");
        System.out.printf("%-50s%-1s%n","average_of_wingspan: ","вывести среднее значение поля wingspan для всех элементов коллекции");
        System.out.printf("%-50s%-1s%n","count_less_than_color {color}: ","вывести количество элементов, значение поля color которых равно заданному");
        System.out.printf("%-50s%-1s%n","filter_less_than_weight {weight}: ","вывести элементы, значение поля weight которых меньше заданного");
    }

    /**
     * Method to calculate average wingspan
     */
    public void avgWingspanCommand(){
        Map<Long, Dragon> collection = CollectionController.getCollection();
        Set<Long> keys = collection.keySet();
        Float wingspan = 0f;
        for (Long temp : keys){
            wingspan += collection.get(temp).getWingspan();
        }
        System.out.println(wingspan/ keys.size());
    }

    /**
     * Method to filter elements by weight
     */
    public void filterWeight(){
        Map<Long, Dragon> collection = CollectionController.getCollection();
        Set<Long> keys = collection.keySet();
        for (Long temp : keys){
            if (collection.get(temp).getWeight() < weight) System.out.println(collection.get(temp));
        }
    }

    /**
     * Method to filter elements by color value
     */
    public void colorCommand(){
        Map<Long, Dragon> collection = CollectionController.getCollection();
        Set<Long> keys = collection.keySet();
        for (Long temp : keys){
            if (collection.get(temp).getColor().value < color ) System.out.println(collection.get(temp));;
        }
    }

    /**
     * Method to get commands history
     */
    public void historyCommand(){
        for (int i = 0; i < 14; i++){
            if (DataController.temp[i] != null) {
                if (i + 1 != 14) System.out.print(i+1+":"+DataController.temp[i]+", ");
                else System.out.println(i+1+":"+DataController.temp[i]);
            }
        }
        System.out.println();
    }

    /**
     * Method for replacing values if they less than the given one id
     */
    public void replaceGreaterCommand(){
        Map<Long, Dragon> collection = CollectionController.getCollection();
        Set<Long> keys = collection.keySet();
        if (keys.contains(id)){
            DataController data = new DataController("without id");
            collection.get(id).setName(data.getName());
            if (collection.get(id).getAge() < data.getAge()) collection.get(id).setAge(data.getAge());
            if (collection.get(id).getWeight() < data.getWeight()) collection.get(id).setWeight(data.getWeight());
            if (collection.get(id).getWingspan() < data.getWingspan()) collection.get(id).setWingspan(data.getWingspan());
            if (collection.get(id).getHead() < data.getHeadSize()) collection.get(id).setHead(data.getHeadSize());
            if (collection.get(id).getCoordinateX() < data.getCoordinateX()) collection.get(id).setCoordinates(data.getCoordinateX(), collection.get(id).getCoordinateY());
            if (collection.get(id).getCoordinateY() < data.getCoordinateY()) collection.get(id).setCoordinates(collection.get(id).getCoordinateX(), data.getCoordinateY());
            if (collection.get(id).getColor().value < data.getColor().value) collection.get(id).setColor(data.getColor());
        }else System.out.println("Элемента с таким айди нет!");
    }

    /**
     * Method for replacing values if they greater than the given one id
     */
    public void replaceLowerCommand(){
        Map<Long, Dragon> collection = CollectionController.getCollection();
        Set<Long> keys = collection.keySet();
        if (keys.contains(id)){
            DataController data = new DataController("without id");
            collection.get(id).setName(data.getName());
            if (collection.get(id).getAge() > data.getAge()) collection.get(id).setAge(data.getAge());
            if (collection.get(id).getWeight() > data.getWeight()) collection.get(id).setWeight(data.getWeight());
            if (collection.get(id).getWingspan() > data.getWingspan()) collection.get(id).setWingspan(data.getWingspan());
            if (collection.get(id).getHead() > data.getHeadSize()) collection.get(id).setHead(data.getHeadSize());
            if (collection.get(id).getCoordinateX() > data.getCoordinateX()) collection.get(id).setCoordinates(data.getCoordinateX(), collection.get(id).getCoordinateY());
            if (collection.get(id).getCoordinateY() > data.getCoordinateY()) collection.get(id).setCoordinates(collection.get(id).getCoordinateX(), data.getCoordinateY());
            if (collection.get(id).getColor().value > data.getColor().value) collection.get(id).setColor(data.getColor());
        }else System.out.println("Элемента с таким айди нет!");
    }

    /**
     * Method for read script file
     */
    public void readScriptFile(){
        try {
            DataController.fis = new FileInputStream(DataController.fileName);
            DataController.isr = new InputStreamReader(DataController.fis);
            Scanner scanner = new Scanner(DataController.isr);
            while (true) DataController.line.add(scanner.nextLine());
        }catch (FileNotFoundException e){
        System.err.println("Ошибка. Файл не найден");
        }catch (NoSuchElementException e){
        }catch (NullPointerException e){
        }finally {
            try{
                DataController.fis.close();
            }catch (IOException | NullPointerException e){}
            try{
                DataController.isr.close();
            }catch (IOException | NullPointerException e){}
    }
}
}

