package server;

import com.company.sourse.Color;
import com.company.sourse.Dragon;
import com.company.utilities.DataController;
import server.Collection.CollectionDownloader;
import server.Collection.CollectionSorter;
import server.Collection.CollectionWriter;
import server.user.UserController;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to full manage commands on server
 */
public class CommandHandlerServer implements Serializable {
    private static String command;
    private static String argument;
    private static ByteArrayOutputStream byteArrayOutputStream;
    private static ObjectOutputStream objectOutputStream;
    static CollectionWriter collectionWriter = new CollectionWriter();
    public volatile static boolean flag = false;
    private static boolean flag_checker = false;

    public CommandHandlerServer(String command, String argument) {
        CommandHandlerServer.command = command;
        CommandHandlerServer.argument = argument;
    }

    public CommandHandlerServer(String command) {
        CommandHandlerServer.command = command;
    }

    /**
     * Execute commands
     *
     * @return answer to send to client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ByteArrayOutputStream executeCommand() throws IOException, ClassNotFoundException, SQLException {
        byteArrayOutputStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        switch (command) {
            case ("show"):
                return showCommand();
            case ("help"):
                return helpCommand();
            case ("info"):
                return infoCommand();
            case ("history"):
                return historyCommand();
            case ("average_of_wingspan"):
                return avgWingspanCommand();
            case ("add"):
                return addCommand();
            case ("exit"):
                Server.log.logger.info("Клиент " + Server.socketAddress + " закончил работу");
                break;
            case ("update"):
                return updateCommand();
            case ("remove_key"):
                return removeKeyCommand(argument);
            case ("insert"):
                return insertCommand();
            case ("execute_script"):
                objectOutputStream.writeObject("Переход в скриптовый режим");
                objectOutputStream.flush();
                return byteArrayOutputStream;
            case ("count_less_than_color"):
                return colorCommand();
            case ("filter_less_than_weight"):
                return filterWeightCommand();
        }
        return null;
    }

    /**
     * Method to show collection
     *
     * @return collection to client
     * @throws IOException
     */
    public static ByteArrayOutputStream showCommand() throws IOException {
        ConcurrentHashMap<Long, Dragon> collection = CollectionSorter.getCollection();
        //objectOutputStream.writeObject(collection.values().stream().reduce("", (concat, x) -> concat += x, String::format).trim());
        objectOutputStream.writeObject(CollectionSorter.getCollectionForUser());
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    /**
     * Method to get commands
     *
     * @return commands to client
     * @throws IOException
     */
    public static ByteArrayOutputStream helpCommand() throws IOException {
        StringBuilder data = new StringBuilder();
        data.append(String.format("%-50s%-1s%n", "info: ", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.) "));
        data.append(String.format("%-50s%-1s%n", "show: ", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"));
        data.append(String.format("%-50s%-1s%n", "insert {id}: ", "добавить новый элемент с заданным ключом"));
        data.append(String.format("%-50s%-1s%n", "update {id}: ", "обновить значение элемента коллекции, id которого равен заданному"));
        data.append(String.format("%-50s%-1s%n", "remove_key {id}: ", "удалить элемент из коллекции по его ключу"));
        data.append(String.format("%-50s%-1s%n", "add: ", "добавить новый элемент "));
        data.append(String.format("%-50s%-1s%n", "execute_script {file_name}(абсолютный):", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме."));
        data.append(String.format("%-50s%-1s%n", "exit: ", "завершить программу (без сохранения в файл)"));
        data.append(String.format("%-50s%-1s%n", "history: ", "вывести последние 14 команд (без их аргументов)"));
        data.append(String.format("%-50s%-1s%n", "average_of_wingspan: ", "вывести среднее значение поля wingspan для всех элементов коллекции"));
        data.append(String.format("%-50s%-1s%n", "count_less_than_color {color}: ", "вывести количество элементов, значение поля color которых равно заданному"));
        data.append(String.format("%-50s%-1s%n", "filter_less_than_weight {weight}: ", "вывести элементы, значение поля weight которых меньше заданного"));
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    /**
     * Method to get info
     *
     * @return info to client
     * @throws IOException
     */
    public static ByteArrayOutputStream infoCommand() throws IOException {
        StringBuilder data = new StringBuilder();
        data.append(String.format("Инициализация: " + UserController.lastInit + "\n"));
        data.append(String.format("Тип коллекции: " + CollectionDownloader.getCollection().getClass().getName() + "\n"));
        data.append(String.format("Количество элементов в коллекции: " + CollectionDownloader.getCollection().size()));
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    /**
     * Method to show command history to client
     *
     * @return command history
     * @throws IOException
     */
    public static ByteArrayOutputStream historyCommand() throws IOException {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            if (Server.temp[i] != null) {
                if (i + 1 != 14) data.append(String.format(i + 1 + ":" + Server.temp[i] + ", "));
                else data.append(String.format(i + 1 + ":" + Server.temp[i]));
            }
        }
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    /**
     * Method to get average of wingspan to client
     *
     * @return average of wingspan
     * @throws IOException
     */
    public static ByteArrayOutputStream avgWingspanCommand() throws IOException {
        Map<Long, Dragon> collection = CollectionDownloader.getCollection();
        Set<Long> keys = collection.keySet();
        objectOutputStream.writeObject(collection.values().stream().reduce(0f, (sum, x) -> sum += x.getWingspan(), Float::sum) / keys.size());
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    /**
     * Method to remove object by key
     *
     * @param argument key
     * @return collection without removed object
     * @throws IOException
     */
    public static ByteArrayOutputStream removeKeyCommand(String argument) throws IOException, SQLException {
        if (CollectionSorter.getCollection().get(Long.parseLong(argument)) != null) {
            if (CollectionSorter.getCollection().get(Long.parseLong(argument)).getUser().trim()
                    .equals(Receiver.getUser().getUsername())) {
                CollectionSorter.collection.remove(Long.parseLong(argument));
                collectionWriter.remover(Long.parseLong(argument));
                objectOutputStream.writeObject("Элемент успешно удалён.");
            } else {
                objectOutputStream.writeObject("Данный объект принадлежит другому пользователю.");
            }
        } else {
            objectOutputStream.writeObject("Удалять нечего, ведь такого айди нет в коллекции...");
        }
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }


    /**
     * Method to add new Dragon
     *
     * @return message to client that new Dragon added.
     * @throws IOException
     */
    public static ByteArrayOutputStream addCommand() throws IOException, SQLException {
        DataController.generateId();
        DataController.generateCreationDate();
        CollectionSorter.collection.put(DataController.getId(), CollectionSorter.collection.get(0L));
        CollectionSorter.collection.remove(0L);
        CollectionSorter.collection.get(DataController.getId()).setId(DataController.getId());
        CollectionSorter.collection.get(DataController.getId()).setCreationDate(DataController.getCreationDate());
        CollectionSorter.collection.get(DataController.getId()).setUsername(Receiver.getUser().getUsername());
        collectionWriter.writer(CollectionSorter.getCollection());
        objectOutputStream.writeObject("Элемент с id " + DataController.getId() + " успешно добавлен.");
        objectOutputStream.flush();
        flag = false;
        return byteArrayOutputStream;
    }

    /**
     * Method to update object by id
     *
     * @return message to client that Dragon was updated
     * @throws IOException
     */
    public static ByteArrayOutputStream updateCommand() throws IOException, SQLException {
        if (CollectionSorter.getCollection().get(Long.parseLong(argument)) != null) {
            if (CollectionSorter.getCollection().get(Long.parseLong(argument)).getUser().trim()
                    .equals(Receiver.getUser().getUsername())) {
                if (!flag) {
                    Server.sendErrorToClient("false");
                    flag_checker = true;
                    return null;
                }
                while (flag) {
                    DataController.generateCreationDate();
                    CollectionSorter.collection.remove(Long.parseLong(argument));
                    CollectionSorter.collection.put(Long.parseLong(argument), CollectionSorter.collection.get(0L));
                    CollectionSorter.collection.remove(0L);
                    CollectionSorter.collection.get(Long.parseLong(argument)).setId(Long.parseLong(argument));
                    CollectionSorter.collection.get(Long.parseLong(argument)).setCreationDate(DataController.getCreationDate());
                    collectionWriter.rewriter(Long.parseLong(argument));
                    objectOutputStream.writeObject("Элемент успешно обновлён");
                    flag = false;
                    flag_checker = false;
                    objectOutputStream.flush();
                    return byteArrayOutputStream;
                }
            } else {
                Server.sendErrorToClient("true");
                objectOutputStream.writeObject("Объект принадлежит не вам.");
            }
        } else {
            Server.sendErrorToClient("true");
            objectOutputStream.writeObject("Такого айди нет в коллекции.");
        }
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    /**
     * Method to insert new Dragon with selected id
     *
     * @return messsage to client that Dragon was succesful inserted
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ByteArrayOutputStream insertCommand() throws IOException, ClassNotFoundException, SQLException {
        if (CollectionSorter.getCollection().get(Long.parseLong(argument)) == null) {
            if (!flag) {
                Server.sendErrorToClient("false");
                flag_checker = true;
                return null;
            }
            while (flag) {
                DataController.generateCreationDate();
                CollectionSorter.collection.put(Long.parseLong(argument), CollectionSorter.collection.get(0L));
                CollectionSorter.collection.remove(0L);
                CollectionSorter.collection.get(Long.parseLong(argument)).setId(Long.parseLong(argument));
                CollectionSorter.collection.get(Long.parseLong(argument)).setCreationDate(DataController.getCreationDate());
                collectionWriter.rewriter(Long.parseLong(argument));
                objectOutputStream.writeObject("Элемент успешно добавлен");
                flag = false;
                flag_checker = false;
                objectOutputStream.flush();
                return byteArrayOutputStream;
            }
        } else {
            Server.sendErrorToClient("true");
            objectOutputStream.writeObject("Этот айди уже есть в коллекции.");
            objectOutputStream.flush();
        }
        return byteArrayOutputStream;
    }

    /**
     * Method to filter collection by weight
     *
     * @return filtered collection
     * @throws IOException
     */
    public static ByteArrayOutputStream filterWeightCommand() throws IOException {
        ConcurrentHashMap<Long, Dragon> collection = CollectionSorter.getCollection();
        objectOutputStream.writeObject(collection.values().stream().filter(x -> x.getWeight() < Long.parseLong(argument))
                .reduce("", (concat, x) -> concat += x, String::format));
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    /**
     * Method to filter collection by color
     *
     * @return filtered collection
     * @throws IOException
     */
    public static ByteArrayOutputStream colorCommand() throws IOException {
        Map<Long, Dragon> collection = CollectionSorter.getCollection();
        objectOutputStream.writeObject("Всего объектов с \"меньшим\" цветом: " + collection.values().stream()
                .filter(x -> x.getColor().value < Color.valueOf(argument.toUpperCase(Locale.ROOT)).value).count() + "\n"
                + collection.values().stream().filter(x -> x.getColor().value < Color.valueOf(argument.toUpperCase(Locale.ROOT)).value)
                .reduce("", (concat, x) -> concat += x, String::format));
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj);
    }
}
