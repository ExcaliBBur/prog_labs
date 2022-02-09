package com.company.utilities;


import com.company.sourse.Dragon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * File controller class
 */
public class FileController {
    final private String file = "lab5";
    final private String env = System.getenv().get(file);
    public static LinkedHashMap<Long, Dragon> collection= new LinkedHashMap<>();
    private boolean flag = false;
    public static java.util.Date lastInit;
    private FileInputStream fis = null;
    private InputStreamReader isr = null;
    private PrintWriter writer = null;
    private FileWriter fileWriter = null;
    public FileController(){
        lastInit();
    }

    /**
     * Method to set last initialization time
     */
    public void lastInit(){
        lastInit = new Date();
    }

    /**
     * Method to read collection from file
     * @return collection
     */
    public LinkedHashMap<Long,Dragon> readCollection(){
        if (env != null){
            try {
                fis = new FileInputStream(env);
                isr = new InputStreamReader(fis);
                BufferedReader reader = new BufferedReader(isr);
                Type type = new TypeToken<LinkedHashMap<Long,Dragon>>(){}.getType();
                try {
                    String line;
                    StringBuilder coll = new StringBuilder();
                    while ((line = reader.readLine()) != null) coll.append(line);
                    collection = new Gson().fromJson(String.valueOf(coll), type);
                }catch (NumberFormatException e){
                    System.err.println("Ошибка. Какой-то нехороший человек изменил исходный файл (вместо числа затаилась строка).");
                    System.out.println("Вывожу список доступных команд:");
                    CommandController.helpCommand();
                }catch (JsonSyntaxException e){
                    System.err.println("Ошибка. Вы сделали что-то непоправимое в исходном файле");
                }catch (IOException e) {}
                try {
                    Set<Long> keys = collection.keySet();
                    for (Long key : keys) {
                        if (collection.get(key).getColor() == null){
                            flag = true;
                            System.err.println("Ошибка. Какой-то нехороший человек изменил в исходном файле цвет дракона на неправильный (на месте color написана переменная не из списка)");
                        }
                        try{
                            long id = collection.get(key).getId();
                            if (id != collection.get(id).getId()) break;
                        }catch (NullPointerException e){
                            flag = true;
                            System.err.println("Ошибка. Кто-то поменял айдишник в исходном файле. Советую поменять назад (либо через команду update)");
                        }
                        if (collection.get(key).getName() == null){
                            System.err.println("Ошибка. Кто-то убрал имя дракона из исходного файла (name)");
                            flag = true;
                        }
                        if (collection.get(key).getWingspan() == null){
                            System.err.println("Ошибка. Кто-то убрал поле с количеством крыльев дракона в исходном файле (wingspan)");
                            flag = true;
                        }
                        try{
                            collection.get(key).getHead();
                        }catch (NullPointerException e){
                            System.err.println("Ошибка. Кто-то убрал поле с размером головы дракона в исходном файле (head size)");
                            flag= true;
                        }
                        if (collection.get(key).getCoordinateX() == null) {
                            System.err.println("Ошибка. Кто-то удалил координаты дракона в исходном файле (coordinates)");
                            flag = true;
                        }
                        if (collection.get(key).getAge() == 0){
                            System.err.println("Ошибка. Кто-то удалил(либо изменил) возраст дракона в исходном файле (age)");
                            flag = true;
                        }
                        if (collection.get(key).getWeight() == 0){
                            System.err.println("Ошибка. Кто-то удалил(либо изменил) вес дракона в исходном файле (weight)");
                            flag = true;
                        }
                        if (collection.get(key).getCreationDate() == null){
                            System.err.println("Ошибка. Кто-то удалил дату создания дракона (creation date)");
                            flag = true;
                        }
                    }
                }catch (NullPointerException e){
                    System.err.println("Ошибка. Файл пустой. Хотите записать в него данные?(Да/Нет)");
                    Scanner scanner = new Scanner(System.in);
                    String temp = scanner.nextLine().toLowerCase(Locale.ROOT);
                    if (temp.equals("да")) new CollectionController();
                    else if (temp.equals("нет")){
                        System.out.println("Вывожу список доступных команд: ");
                        CommandController.helpCommand();
                    }
                    else System.out.println("Введите \"Да\" или \"Нет\"");
                }
                if (!collection.isEmpty() && !flag )System.out.println("Коллекция успешно загружена из файла!");
                else System.out.println("Коллекция загружена с ошибками. Поправьте их, пожалуйста...");
                return collection;
            }catch (FileNotFoundException e){
                System.err.println("Ошибка. Файл не найден");
            }finally {
                try {
                    fis.close();
                } catch (IOException | NullPointerException e) {}
                try {
                    isr.close();
                } catch (IOException | NullPointerException e) {}
            }
        }
        else System.err.println("Ошибка. Не найдена переменная окружения");
        return new LinkedHashMap<>();
    }

    /**
     * Method to write collection to file
     */
    public void writeCollection() {
        if (env != null) {
            try {
                fileWriter = new FileWriter(env);
                writer = new PrintWriter(fileWriter);
                String list = new GsonBuilder().setPrettyPrinting().create().toJson(CollectionController.getCollection());
                writer.write(list);
                System.out.println("Коллекция успешно записана в файл!");
            }catch (IOException e) {
                e.toString();
            }finally {
                try{
                    fileWriter.close();
                }catch (IOException | NullPointerException e){
                }
                try{
                    writer.close();
                }catch (NullPointerException e){
                    System.err.println("Ошибка. Файл не найден => сохранять коллекцию некуда");
                }
            }
        }else System.err.println("Ошибка. Не найдена переменная окружения");
    }

    /**
     * Method to write space to file
     */
    public void writeSpace(){
        if (env != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(env))) {
                String list = "";
                writer.write(list);
            }catch (IOException e) {
                e.toString();
            }
        }else System.err.println("Ошибка. Не найдена переменная окружения");
    }

    /**
     * Method to get collection
     * @return collection
     */
    public static LinkedHashMap<Long, Dragon> getCollection(){
        return collection;
    }
    @Override
    public boolean equals(Object o){
        return this == o;
    }
}
