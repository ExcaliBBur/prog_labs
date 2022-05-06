package server.user;

import com.company.utilities.Requester;
import com.company.utilities.User;
<<<<<<< HEAD
import server.Collection.Connectivity;
=======
import server.Connectivity;
>>>>>>> 9f538714d29227ba0f15670f8aa7bc0599191580
import server.Const;
import com.company.utilities.Response;
import server.Receiver;
import server.Server;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.RecursiveAction;

public class UserController extends RecursiveAction implements Connectivity {
    Connection connection;
    User user;
    public static java.util.Date lastInit;


    public UserController() {
    }

    @Override
    protected void compute() {
        getJDBCConnection();
        if (user.getType().equals(User.Type.AUTH)) authUser();
        else if (user.getType().equals(User.Type.REGISTRATION)) {
            try {
                registrateUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
        }
    }
    @Override
    public void getJDBCConnection() {
        try {
            connection = DriverManager.getConnection(Const.jdbcURL, Const.NAME_ADMIN, Const.PASSWORD_ADMIN);
            user = Receiver.getUser();
        } catch (SQLException e) {
            System.out.println("Невозможно подключиться к БД");
        }
    }

    private void registrateUser() throws IOException {
<<<<<<< HEAD
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO " + Const.USER_TABLE + " values (?,?)")) {
=======
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO " + Const.USER_TABLE + " values (?,?) ;")) {
>>>>>>> 9f538714d29227ba0f15670f8aa7bc0599191580
            if (isUserExist()) {
                Server.sendResponseToClient(new Requester(Response.REG_ERROR));
            } else {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.executeUpdate();
                Server.sendResponseToClient(new Requester(Response.OK));
                lastInit = new java.util.Date();
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("some problems with DB...");
        }
    }

    private boolean isUserExist() {
<<<<<<< HEAD
        try (PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM " + Const.USER_TABLE + " where " +
                Const.USERNAME + " = ?")) {
=======
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + Const.USER_TABLE + " where " +
                Const.USERNAME + " = ? ;")) {
>>>>>>> 9f538714d29227ba0f15670f8aa7bc0599191580
            ps.setString(1, user.getUsername());
            try (ResultSet resultSet = ps.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void authUser() {
<<<<<<< HEAD
        try (PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM " + Const.USER_TABLE + " where "
                + Const.USERNAME + " = ? " + "and " + Const.PASSWORD + " = ?")) {
=======
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + Const.USER_TABLE + " WHERE "
                + Const.USERNAME + "= ? " + "and " + Const.PASSWORD + "= ? ;")) {
>>>>>>> 9f538714d29227ba0f15670f8aa7bc0599191580
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            try (ResultSet resultSet = ps.executeQuery()) {
                if (!resultSet.next()) {
                    Server.sendResponseToClient(new Requester(Response.AUTH_ERROR));
                } else {
<<<<<<< HEAD
                    Server.sendResponseToClient(new Requester(Response.OK));
                    lastInit = new java.util.Date();
=======
                    lastInit = new java.util.Date();
                    Server.sendResponseToClient(new Requester(Response.OK));
>>>>>>> 9f538714d29227ba0f15670f8aa7bc0599191580
                    connection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
