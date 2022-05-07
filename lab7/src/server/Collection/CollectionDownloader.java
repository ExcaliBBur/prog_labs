package server.Collection;

import com.company.sourse.Color;
import com.company.sourse.Coordinates;
import com.company.sourse.Dragon;
import com.company.sourse.DragonHead;
import server.AppServer;
import server.Const;


import java.io.Console;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveAction;

/**
 * Class to download collection from DB
 */
public class CollectionDownloader extends RecursiveAction implements Connectivity{
    private static Connection connection;
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0
    private Float wingspan; //Значение поля должно быть больше 0, Поле не может быть null
    private long weight; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonHead head;
    public static String username;
    static ConcurrentHashMap<Long, Dragon> collection = new ConcurrentHashMap<>();


    public CollectionDownloader(boolean connectivity) {
        getJDBCConnection();
    }
    public CollectionDownloader() {};
    /**
     * Method to FJP
     */
    @Override
    protected void compute() {
        getCollectionFromDB();
    }
    public static void getPasswordForDB() {
        Console console = System.console();
        if (console != null) {
            Const.PASSWORD_ADMIN = String.valueOf(console.readPassword("Введите пароль админа к БД: "));
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите пароль админа к БД: ");
            Const.PASSWORD_ADMIN = scanner.nextLine();
        }
        getTestJDBCConnection();
        AppServer.flag = true;
    }
    /**
     * Method to get connection with DB
     */
    @Override
    public void getJDBCConnection() {
        try {
            connection = DriverManager.getConnection(Const.jdbcURL, Const.NAME_ADMIN, Const.PASSWORD_ADMIN);
        } catch (SQLException e) {
            System.out.println("Невозможно подключиться к БД");
        }
    }
    /**
     * Method to get test connection with DB
     */
    public static void getTestJDBCConnection() {
        try {
            DriverManager.getConnection(Const.jdbcURL, Const.NAME_ADMIN, Const.PASSWORD_ADMIN);
        } catch (SQLException e) {
            System.out.println("Пароль к БД неверный.");
            getPasswordForDB();
        }
    }
    /**
     * Method to get collection from DB
     */
    public void getCollectionFromDB() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + Const.DRAGON_TABLE );
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                name = resultSet.getString(2).trim();
                coordinates = new Coordinates(resultSet.getDouble(3), resultSet.getLong(4));
                String temp = resultSet.getString(5).trim();
                DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                creationDate = dateFormat.parse(temp);
                age = resultSet.getInt(6);
                wingspan = resultSet.getFloat(7);
                weight = resultSet.getLong(8);
                color = Color.valueOf(resultSet.getString(9).trim());
                head = new DragonHead(resultSet.getLong(10));
                username = resultSet.getString(11).trim();
                convertateCollection();
            }
        } catch (SQLException e) {
            System.out.println("Невозможно подключиться к БД");
        } catch (ParseException e) {
            System.out.println("Ошибка при переводе даты");
        }
    }

    /**
     * Method to convertate data from DB to collection
     */
    private void convertateCollection() {
        Dragon dragon = new Dragon(id, name, coordinates, creationDate, age, wingspan, weight, color, head, username);
        collection.put(dragon.getId(), dragon);
    }

    /**
     * Method to get collection
     * @return collection
     */
    public static ConcurrentHashMap<Long, Dragon> getCollection() {
        return collection;
    }

}
