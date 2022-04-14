package client;

import com.company.exceptions.IncorrectIdException;
import com.company.sourse.Color;
import com.company.sourse.Dragon;
import com.company.utilities.DataController;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Command controller client class
 */
public class CommandHandlerClient {

    Scanner scanner = new Scanner(System.in);
    public static LinkedHashMap<Long, Dragon> collection = new LinkedHashMap<>();
    public static String argument;
    public static String command;
    public static String error = "";
    private static boolean isScriptFile = true;

    /**
     * Full command manager client
     *
     * @throws IOException
     * @throws ClassNotFoundException
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
                    } catch (InterruptedException ignored) {
                    }
                    argument = temp[1];
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            ;
            switch (command) {
                case ("show"):
                case ("help"):
                case ("info"):
                case ("clear"):
                case ("history"):
                case ("average_of_wingspan"):
                    Client.sendCommand(new CommandSerialize(command), 1);
                    break;
                case ("add"):
                    Client.sendCommand(new CommandSerialize(command), 1);
                    addCommand();
                    break;
                case ("exit"):
                    Client.sendCommand(new CommandSerialize(command), 1);
                    Client.exit();
                    break;
                case ("replace_if_greater"):
                case ("replace_if_lower"):
                case ("update"):
                    try {
                        if (Integer.parseInt(argument) <= 0) throw new IncorrectIdException();
                        Client.sendCommand(new CommandSerialize(command, argument), 1);
                        Client.receiveError();
                        if (error.equals("false")) addCommand();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                        new CommandHandlerClient();
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                        new CommandHandlerClient();
                    } catch (IncorrectIdException e) {
                        System.err.println("Ошибка. Id должен быть больше нуля.");
                        new CommandHandlerClient();
                    }
                    break;
                case ("remove_key"):
                    try {
                        if (Integer.parseInt(argument) <= 0) throw new IncorrectIdException();
                        Client.sendCommand(new CommandSerialize(command, argument), 1);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                        new CommandHandlerClient();
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                        new CommandHandlerClient();
                    } catch (IncorrectIdException e) {
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
                        Client.sendCommand(new CommandSerialize(command, argument), 1);
                        Client.receiveError();
                        if (error.equals("false")) insertCommand();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды.");
                        new CommandHandlerClient();
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                        new CommandHandlerClient();
                    } catch (IncorrectIdException e) {
                        System.err.println("Ошибка. Id должен быть больше нуля.");
                        new CommandHandlerClient();
                    }
                    break;
                case ("execute_script"):
                    try {
                        readScriptFile();
                        if (isScriptFile) {
                            Client.sendCommand(new CommandSerialize(command, argument), 1);
                            DataController.flag = false;
                            executeScript();
                        }else new CommandHandlerClient();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели название файла");
                        new CommandHandlerClient();
                    }
                    break;
                case ("count_less_than_color"):
                    try {
                        Color.valueOf(argument.toUpperCase(Locale.ROOT));
                        Client.sendCommand(new CommandSerialize(command, argument), 1);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Ошибка. Вы не ввели аргумент команды");
                        new CommandHandlerClient();
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка. Ввёден неправильный аргумент команды");
                        new CommandHandlerClient();
                    } catch (IllegalArgumentException e) {
                        System.err.println("Ошибка. Данного цвета нет в списках.");
                        new CommandHandlerClient();
                    }
                    break;
                case ("filter_less_than_weight"):
                    try {
                        Long.parseLong(argument);
                        Client.sendCommand(new CommandSerialize(command, argument), 1);
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
                    if (!DataController.flag) {
                        try {
                            DataController.line.removeFirst();
                            DataController.strochka = DataController.line.getFirst();
                        } catch (NoSuchElementException ignored) {
                            DataController.flag = true;
                            System.out.println("Переход в консольный режим...");
                        }
                    }
                    new CommandHandlerClient();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Ошибка. Вы не ввели команду");
            if (!DataController.flag) {
                try {
                    DataController.line.removeFirst();
                    DataController.strochka = DataController.line.getFirst();
                } catch (NoSuchElementException ignored) {
                    DataController.flag = true;
                    System.out.println("Переход в консольный режим...");
                }
            }
            new CommandHandlerClient();
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    /**
     * Method to add Dragon
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void addCommand() throws IOException, ClassNotFoundException {
        DataController data = new DataController();
        Dragon dragon = new Dragon(DataController.getId(), data.getName(), data.getCoordinates(), DataController.getCreationDate(), data.getAge(), data.getWingspan(),
                data.getWeight(), data.getColor(), data.getHead());
        collection.put(dragon.getId(), dragon);
        Client.sendCommand(collection, 0);
        collection.clear();
    }

    /**
     * Method to insert Dragon
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void insertCommand() throws IOException, ClassNotFoundException {
        DataController data = new DataController("without id");
        Dragon dragon = new Dragon(DataController.getId(), data.getName(), data.getCoordinates(), DataController.getCreationDate(), data.getAge(), data.getWingspan(),
                data.getWeight(), data.getColor(), data.getHead());
        collection.put(dragon.getId(), dragon);
        Client.sendCommand(collection, 0);
        collection.clear();
    }

    /**
     * Method to read script file for execute_script command
     */
    public void readScriptFile() throws IOException, ClassNotFoundException {
        try {
            DataController.fis = new FileInputStream(argument);
            DataController.isr = new InputStreamReader(DataController.fis);
            Scanner scanner = new Scanner(DataController.isr);
            while (true) DataController.line.add(scanner.nextLine());
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка. Файл не найден");
            isScriptFile = false;
        } catch (NoSuchElementException | NullPointerException e) {
        } finally {
            try {
                DataController.fis.close();
            } catch (IOException | NullPointerException ignored) {
            }
            try {
                DataController.isr.close();
            } catch (IOException | NullPointerException ignored) {
            }
        }
    }

    /**
     * Method to execute script
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void executeScript() throws IOException, ClassNotFoundException {
        boolean temp = false;
        while (DataController.line.size() != 0) {
            DataController.strochka = DataController.line.getFirst();
            Client.receiveAnswer();
            new CommandHandlerClient();
            try {
                DataController.line.removeFirst();
            } catch (NoSuchElementException ignored) {
                temp = true;
            }
            ;
        }
        if (!temp) {
            DataController.flag = true;
            System.out.println("Переход в консольный режим...");
        }
    }
}

