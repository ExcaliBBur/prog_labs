package server;

import com.company.sourse.Color;
import com.company.sourse.Dragon;
import com.company.utilities.DataController;

import java.io.*;
import java.util.*;

/**
 * Class to full manage commands on server
 */
public class CommandHandlerServer implements Serializable {
    private static String command;
    private static String argument;
    private static ByteArrayOutputStream byteArrayOutputStream;
    private static ObjectOutputStream objectOutputStream;

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
    public static ByteArrayOutputStream executeCommand() throws IOException, ClassNotFoundException {
        byteArrayOutputStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        switch (command) {
            case ("show"):
                return showCommand();
            case ("help"):
                return helpCommand();
            case ("info"):
                return infoCommand();
            case ("clear"):
                return clearCommand();
            case ("history"):
                return historyCommand();
            case ("average_of_wingspan"):
                return avgWingspanCommand();
            case ("add"):
                return addCommand();
            case ("exit"):
                Server.log.logger.info("Клиент " + Server.socketAddress + " закончил работу");
                saveCommand();
                break;
            case ("update"):
                return updateCommand();
            case ("remove_key"):
                return removeKeyCommand(argument);
            case ("replace_if_greater"):
                return replaceGreaterCommand();
            case ("replace_if_lower"):
                return replaceLowerCommand();
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
        Map<Long, Dragon> collection = CollectionSorter.getCollection();
        objectOutputStream.writeObject(collection.values().stream().reduce("", (concat, x) -> concat += x, String::format).trim());
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
        data.append(String.format("%-50s%-1s%n", "clear: ", "очистить коллекцию(сразу из файла)"));
        data.append(String.format("%-50s%-1s%n", "execute_script {file_name}(абсолютный):", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме."));
        data.append(String.format("%-50s%-1s%n", "exit: ", "завершить программу (без сохранения в файл)"));
        data.append(String.format("%-50s%-1s%n", "history: ", "вывести последние 14 команд (без их аргументов)"));
        data.append(String.format("%-50s%-1s%n", "replace_if_greater {id}: ", "заменить значение по ключу, если новое значение больше старого"));
        data.append(String.format("%-50s%-1s%n", "replace_if_lower {id}: ", "заменить значение по ключу, если новое значение меньше старого"));
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
        data.append(String.format("Инициализация: " + FileController.lastInit + "\n"));
        data.append(String.format("Тип коллекции: " + FileController.collection.getClass().getName() + "\n"));
        data.append(String.format("Количество элементов в коллекции: " + FileController.collection.size()));
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    /**
     * Method to clear collection directly from the file
     *
     * @return message that collection cleared to client
     * @throws IOException
     */
    public static ByteArrayOutputStream clearCommand() throws IOException {
        StringBuilder data = new StringBuilder();
        CollectionSorter.collection.clear();
        FileController file = new FileController();
        file.writeSpace();
        data.append(String.format("Коллекция успешно очищена"));
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
        Map<Long, Dragon> collection = CollectionSorter.getCollection();
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
    public static ByteArrayOutputStream removeKeyCommand(String argument) throws IOException {
        if (FileController.getCollection().get(Long.parseLong(argument)) != null) {
            CollectionSorter.collection.remove(Long.parseLong(argument));
            objectOutputStream.writeObject("Элемент успешно удалён.");
            objectOutputStream.flush();
            return byteArrayOutputStream;
        } else {
            objectOutputStream.writeObject("Удалять нечего, ведь такого айди нет в коллекции...");
            objectOutputStream.flush();
            return byteArrayOutputStream;
        }
    }

    /**
     * Method to replace if lower
     *
     * @return object replaced if lower
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ByteArrayOutputStream replaceLowerCommand() throws IOException, ClassNotFoundException {
        if (FileController.getCollection().get(Long.parseLong(argument)) != null) {
            Server.sendErrorToClient("false");
            Server.receiveCollection();
            CollectionSorter.collection.get(Long.parseLong(argument)).setName(CollectionSorter.collection.get(0L).getName());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getAge() > CollectionSorter.collection.get(0L).getAge())
                CollectionSorter.collection.get(Long.parseLong(argument)).setAge(CollectionSorter.collection.get(0L).getAge());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getWeight() > CollectionSorter.collection.get(0L).getWeight())
                CollectionSorter.collection.get(Long.parseLong(argument)).setWeight(CollectionSorter.collection.get(0L).getWeight());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getWingspan() > CollectionSorter.collection.get(0L).getWingspan())
                CollectionSorter.collection.get(Long.parseLong(argument)).setWingspan(CollectionSorter.collection.get(0L).getWingspan());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getHead() > CollectionSorter.collection.get(0L).getHead())
                CollectionSorter.collection.get(Long.parseLong(argument)).setHead(CollectionSorter.collection.get(0L).getHead());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getCoordinateX() > CollectionSorter.collection.get(0L).getCoordinateX())
                CollectionSorter.collection.get(Long.parseLong(argument)).setCoordinates(CollectionSorter.collection.get(0L).getCoordinateX(), CollectionSorter.collection.get(Long.parseLong(argument)).getCoordinateY());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getCoordinateY() > CollectionSorter.collection.get(0L).getCoordinateY())
                CollectionSorter.collection.get(Long.parseLong(argument)).setCoordinates(CollectionSorter.collection.get(Long.parseLong(argument)).getCoordinateX(), CollectionSorter.collection.get(0L).getCoordinateY());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getColor().value > CollectionSorter.collection.get(0L).getColor().value)
                CollectionSorter.collection.get(Long.parseLong(argument)).setColor(CollectionSorter.collection.get(0L).getColor());
            CollectionSorter.collection.remove(0L);
            objectOutputStream.writeObject("Элемент успешно заменён.");
            objectOutputStream.flush();
        } else {
            Server.sendErrorToClient("true");
            objectOutputStream.writeObject("Такого айди нет в коллекции.");
            objectOutputStream.flush();
        }
        return byteArrayOutputStream;
    }

    /**
     * Method to replace if greater
     *
     * @return object replaced if greater
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ByteArrayOutputStream replaceGreaterCommand() throws IOException, ClassNotFoundException {
        if (FileController.getCollection().get(Long.parseLong(argument)) != null) {
            Server.sendErrorToClient("false");
            Server.receiveCollection();
            CollectionSorter.collection.get(Long.parseLong(argument)).setName(CollectionSorter.collection.get(0L).getName());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getAge() < CollectionSorter.collection.get(0L).getAge())
                CollectionSorter.collection.get(Long.parseLong(argument)).setAge(CollectionSorter.collection.get(0L).getAge());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getWeight() < CollectionSorter.collection.get(0L).getWeight())
                CollectionSorter.collection.get(Long.parseLong(argument)).setWeight(CollectionSorter.collection.get(0L).getWeight());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getWingspan() < CollectionSorter.collection.get(0L).getWingspan())
                CollectionSorter.collection.get(Long.parseLong(argument)).setWingspan(CollectionSorter.collection.get(0L).getWingspan());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getHead() < CollectionSorter.collection.get(0L).getHead())
                CollectionSorter.collection.get(Long.parseLong(argument)).setHead(CollectionSorter.collection.get(0L).getHead());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getCoordinateX() < CollectionSorter.collection.get(0L).getCoordinateX())
                CollectionSorter.collection.get(Long.parseLong(argument)).setCoordinates(CollectionSorter.collection.get(0L).getCoordinateX(), CollectionSorter.collection.get(Long.parseLong(argument)).getCoordinateY());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getCoordinateY() < CollectionSorter.collection.get(0L).getCoordinateY())
                CollectionSorter.collection.get(Long.parseLong(argument)).setCoordinates(CollectionSorter.collection.get(Long.parseLong(argument)).getCoordinateX(), CollectionSorter.collection.get(0L).getCoordinateY());
            if (CollectionSorter.collection.get(Long.parseLong(argument)).getColor().value < CollectionSorter.collection.get(0L).getColor().value)
                CollectionSorter.collection.get(Long.parseLong(argument)).setColor(CollectionSorter.collection.get(0L).getColor());
            CollectionSorter.collection.remove(0L);
            objectOutputStream.writeObject("Элемент успешно заменён.");
            objectOutputStream.flush();
        } else {
            Server.sendErrorToClient("true");
            objectOutputStream.writeObject("Такого айди нет в коллекции.");
            objectOutputStream.flush();
        }
        return byteArrayOutputStream;
    }

    /**
     * Method to save collection to file
     */
    public static void saveCommand() {
        FileController file = new FileController();
        file.writeCollection();
    }

    /**
     * Method to add new Dragon
     *
     * @return message to client that new Dragon added.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ByteArrayOutputStream addCommand() throws IOException, ClassNotFoundException {
        Server.receiveCollection();
        DataController.generateId();
        DataController.generateCreationDate();
        CollectionSorter.collection.put(DataController.getId(), CollectionSorter.collection.get(0L));
        CollectionSorter.collection.remove(0L);
        CollectionSorter.collection.get(DataController.getId()).setId(DataController.getId());
        CollectionSorter.collection.get(DataController.getId()).setCreationDate(DataController.getCreationDate());
        objectOutputStream.writeObject("Элемент с id " + DataController.getId() + " успешно добавлен.");
        objectOutputStream.flush();
        return byteArrayOutputStream;
    }

    /**
     * Method to update object by id
     *
     * @return message to client that Dragon was updated
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ByteArrayOutputStream updateCommand() throws IOException, ClassNotFoundException {
        if (FileController.getCollection().get(Long.parseLong(argument)) != null) {
            Server.sendErrorToClient("false");
            Server.receiveCollection();
            DataController.generateCreationDate();
            CollectionSorter.collection.put(Long.parseLong(argument), CollectionSorter.collection.get(0L));
            CollectionSorter.collection.remove(0L);
            CollectionSorter.collection.get(Long.parseLong(argument)).setId(Long.parseLong(argument));
            CollectionSorter.collection.get(Long.parseLong(argument)).setCreationDate(DataController.getCreationDate());
            objectOutputStream.writeObject("Элемент успешно обновлён");
            objectOutputStream.flush();
        } else {
            Server.sendErrorToClient("true");
            objectOutputStream.writeObject("Такого айди нет в коллекции.");
            objectOutputStream.flush();
        }
        return byteArrayOutputStream;
    }

    /**
     * Method to insert new Dragon with selected id
     *
     * @return messsage to client that Dragon was succesful inserted
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ByteArrayOutputStream insertCommand() throws IOException, ClassNotFoundException {
        if (FileController.getCollection().get(Long.parseLong(argument)) == null) {
            Server.sendErrorToClient("false");
            Server.receiveCollection();
            DataController.generateCreationDate();
            CollectionSorter.collection.put(Long.parseLong(argument), CollectionSorter.collection.get(0L));
            CollectionSorter.collection.remove(0L);
            CollectionSorter.collection.get(Long.parseLong(argument)).setId(Long.parseLong(argument));
            CollectionSorter.collection.get(Long.parseLong(argument)).setCreationDate(DataController.getCreationDate());
            objectOutputStream.writeObject("Элемент успешно добавлен");
            objectOutputStream.flush();
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
        Map<Long, Dragon> collection = CollectionSorter.getCollection();
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

}
