package com.company.utilities;

import com.company.sourse.Dragon;

import java.util.*;

public class CommandController {
    Scanner scanner = new Scanner(System.in);
    private long weight;
    private int color;
    private long id;
    public CommandController(){
        String[] command = scanner.nextLine().toLowerCase(Locale.ROOT).split(" ");
        for (int i = 13; i > 0; i --) DataController.temp[i] = DataController.temp[i-1];
        DataController.temp[0] = command[0];
        switch (command[0]){
            case ("show"):
                showCommand();
                break;
            case("exit"):
                System.out.println("Сохранить коллекцию перед выходом? (Да/Нет)");
                String exit = scanner.nextLine().toLowerCase(Locale.ROOT);
                if (exit.equals("да")) saveCommand();
                System.out.println("Завершение работы программы...");
                exitCommand();
                break;
            case("help"):
                helpCommand();
                break;
            case("info"):
                infoCommand();
                break;
            case("insert"):
                try{
                if (FileController.getCollection().get(Long.parseLong(command[1])) != null){
                    System.out.println("Элемент с таким айди уже есть. Хотите заменить его?(Да/Нет)");
                    String temp = scanner.nextLine().toLowerCase(Locale.ROOT);
                    if (temp.equals("да")){
                        new CollectionController(command[1]);
                        System.out.println("Успешно удалено!");
                    }else if (temp.equals("нет")) break;
                    else System.out.println("Такой ответ не предусмотрен большим братом");
                }else new CollectionController(command[1]);
                }
                catch (ArrayIndexOutOfBoundsException e){
                    System.err.println("Ошибка. Вы не ввели аргумент команды.");
                }catch (NumberFormatException e){
                    System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                }
                break;
            case("update"):
                try {
                    if (FileController.getCollection().get(Long.parseLong(command[1])) != null) {
                        new CollectionController(command[1]);
                    } else System.out.println("Такого айди нет в коллекции.");
                }catch (ArrayIndexOutOfBoundsException e){
                    System.err.println("Ошибка. Вы не ввели аргумент команды.");
                }catch (NumberFormatException e){
                    System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                }
                break;
            case("remove_key"):
                try {
                    if (FileController.getCollection().get(Long.parseLong(command[1])) != null) {
                        CollectionController.collection.remove(Long.parseLong(command[1]));
                        CollectionController.sortedCollection.remove(Long.parseLong(command[1]));
                        System.out.println("Коллекция успешно удалена");
                    } else System.out.println("Удалять нечего, ведь такого айди нет в коллекции...");
                }catch (ArrayIndexOutOfBoundsException e){
                    System.err.println("Ошибка. Вы не ввели аргумент команды.");
                }catch (NumberFormatException e){
                    System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                }
                break;
            case("clear"):
                clearCommand();
                break;
            case("save"):
                saveCommand();
                break;
            case("execute_script"):
                //тут есть аргумент
                break;
            case("history"):
                historyCommand();
                break;
            case("replace_if_greater"):
                try {
                    id = Long.parseLong(command[1]);
                    replaceGreaterCommand();
                }catch (ArrayIndexOutOfBoundsException e){
                    System.err.println("Ошибка. Вы не ввели аргумент команды.");
                }catch (NumberFormatException e){
                    System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                }
                break;
            case("replace_if_lower"):
                try {
                    id = Long.parseLong(command[1]);
                    replaceLowerCommand();
                }catch (ArrayIndexOutOfBoundsException e){
                    System.err.println("Ошибка. Вы не ввели аргумент команды.");
                }catch (NumberFormatException e){
                    System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                }
                break;
            case("average_of_wingspan"):
                avgWingspanCommand();
                break;
            case("count_less_than_color"):
                try{
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
                        System.out.println("Ошибка в аргументе. Такого цвета нет в списке");}
                }catch (ArrayIndexOutOfBoundsException e){
                    System.err.println("Ошибка. Вы не ввели аргумент команды");
                }catch (NumberFormatException e){
                    System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                }
                colorCommand();
                break;
            case("filter_less_than_weight"):
                try {
                    weight = Long.parseLong(command[1]);
                    filterWeight();
                }catch (ArrayIndexOutOfBoundsException e){
                    System.err.println("Ошибка. Вы не ввели аргумент команды");
                }catch (NumberFormatException e){
                    System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                }
                break;
            case("add"):
                new CollectionController();
                break;
            default:
                System.err.println("Такой команды не найдено!Введите \"help\" для получения списка команд");
                DataController.temp[0] = null;
                for (int i = 0; i <= 12; i ++) DataController.temp[i] = DataController.temp[i+1];
        }

    }
    public void showCommand(){
        CollectionController.getCollectionForUser();
    }
    public void exitCommand(){
        System.exit(0);
    }
    public void clearCommand(){
        CollectionController.collection.clear();
        CollectionController.sortedCollection.clear();
        FileController file = new FileController();
        file.writeSpace();
        System.out.println("Коллекция успешно очищена");
    }
    public void infoCommand(){
        System.out.println("Инициализация: " + FileController.lastInit);
        System.out.println("Тип коллекции: " + FileController.collection.getClass().getName());
        System.out.println("Количество элементов в коллекции: "+FileController.collection.size());
    }
    public void saveCommand(){
        FileController file = new FileController();
        file.writeCollection();
    }
    public static void helpCommand(){
        System.out.printf("%-40s%-1s%n","info: ","вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.) ");
        System.out.printf("%-40s%-1s%n", "show: ","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        System.out.printf("%-40s%-1s%n","insert {id}: ", "добавить новый элемент с заданным ключом");
        System.out.printf("%-40s%-1s%n","update {id}: ","обновить значение элемента коллекции, id которого равен заданному");
        System.out.printf("%-40s%-1s%n","remove_key {id}: ","удалить элемент из коллекции по его ключу");
        System.out.printf("%-40s%-1s%n","add: ","добавить новый элемент ");
        System.out.printf("%-40s%-1s%n","clear: ","очистить коллекцию");
        System.out.printf("%-40s%-1s%n","save: ", "сохранить коллекцию в файл");
        System.out.printf("%-40s%-1s%n","execute_script file_name:","считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.printf("%-40s%-1s%n","exit: ","завершить программу (без сохранения в файл)");
        System.out.printf("%-40s%-1s%n","history: ", "вывести последние 14 команд (без их аргументов)");
        System.out.printf("%-40s%-1s%n","replace_if_greater {id}: ","заменить значение по ключу, если новое значение больше старого");
        System.out.printf("%-40s%-1s%n","replace_if_lower {id}: ","заменить значение по ключу, если новое значение меньше старого");
        System.out.printf("%-40s%-1s%n","average_of_wingspan: ","вывести среднее значение поля wingspan для всех элементов коллекции");
        System.out.printf("%-40s%-1s%n","count_less_than_color {color}: ","вывести количество элементов, значение поля color которых равно заданному");
        System.out.printf("%-40s%-1s%n","filter_less_than_weight {weight}: ","вывести элементы, значение поля weight которых меньше заданного");
    }
    public void avgWingspanCommand(){
        Map<Long, Dragon> collection = CollectionController.getCollection();
        Set<Long> keys = collection.keySet();
        Float wingspan = 0f;
        for (Long temp : keys){
            wingspan += collection.get(temp).getWingspan();
        }
        System.out.println(wingspan/ keys.size());
    }
    public void filterWeight(){
        Map<Long, Dragon> collection = CollectionController.getCollection();
        Set<Long> keys = collection.keySet();
        for (Long temp : keys){
            if (collection.get(temp).getWeight() < weight) System.out.println(collection.get(temp));
        }
    }
    public void colorCommand(){
        Map<Long, Dragon> collection = CollectionController.getCollection();
        Set<Long> keys = collection.keySet();
        for (Long temp : keys){
            if (collection.get(temp).getColor().value < color ) System.out.println(collection.get(temp));;
        }
    }
    public void historyCommand(){
        for (int i = 0; i < 14; i++){
            if (DataController.temp[i] != null) {
                if (i + 1 != 14) System.out.print(i+1+":"+DataController.temp[i]+", ");
                else System.out.println(i+1+":"+DataController.temp[i]);
            }
        }
        System.out.println();
    }
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
}

