package server.Collection;

import com.company.sourse.Dragon;
import com.company.utilities.DataController;
import server.Const;
import server.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to write/rewrite smth from DB
 */
public class CollectionWriter implements Connectivity{
    private Connection connection;


    public CollectionWriter() {
        getJDBCConnection();
    }

    @Override
    public void getJDBCConnection() {
        try {
            connection = DriverManager.getConnection(Const.jdbcURL, Const.NAME_ADMIN, Const.PASSWORD_ADMIN);
        } catch (SQLException e) {
            System.out.println("Невозможно подключиться к БД");
        }
    }

    /**
     * Method to write collection to DB
     * @param collection - collection
     * @throws SQLException
     */
    public void writer(ConcurrentHashMap<Long, Dragon> collection) throws SQLException {
        String sql = "INSERT INTO " + Const.DRAGON_TABLE + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, collection.get(DataController.getId()).getId());
        preparedStatement.setString(2, collection.get(DataController.getId()).getName());
        preparedStatement.setDouble(3, collection.get(DataController.getId()).getCoordinateX());
        preparedStatement.setDouble(4, collection.get(DataController.getId()).getCoordinateY());
        preparedStatement.setString(5, collection.get(DataController.getId()).getCreationDate().toString());
        preparedStatement.setInt(6, collection.get(DataController.getId()).getAge());
        preparedStatement.setDouble(7, collection.get(DataController.getId()).getWingspan());
        preparedStatement.setDouble(8, collection.get(DataController.getId()).getWeight());
        preparedStatement.setString(9, collection.get(DataController.getId()).getColor().toString());
        preparedStatement.setLong(10, collection.get(DataController.getId()).getHead());
        preparedStatement.setString(11, collection.get(DataController.getId()).getUser());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Server.log.logger.warning("Невозможно записать объект в БД");
        }
    }

    /**
     * Method to remove collection from DB
     * @param id - id which need to delete
     */
    public void remover(Long id) {
        String sql = "DELETE FROM " + Const.DRAGON_TABLE + " WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Server.log.logger.warning("Невозможно удалить объект из БД");
        }
    }

    /**
     * Method to rewrite collection from DB
     * @param id - id which need to rewrite
     * @throws SQLException
     */
    public void rewriter(Long id) throws SQLException {
       ConcurrentHashMap<Long, Dragon> collection = CollectionSorter.getCollection();
        remover(id);
        String sql = "INSERT INTO " + Const.DRAGON_TABLE + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,id);
        preparedStatement.setString(2,collection.get(id).getName());
        preparedStatement.setDouble(3,collection.get(id).getCoordinateX());
        preparedStatement.setDouble(4,collection.get(id).getCoordinateY());
        preparedStatement.setString(5,collection.get(id).getCreationDate().toString());
        preparedStatement.setInt(6,collection.get(id).getAge());
        preparedStatement.setDouble(7,collection.get(id).getWingspan());
        preparedStatement.setDouble(8,collection.get(id).getWeight());
        preparedStatement.setString(9,collection.get(id).getColor().toString());
        preparedStatement.setLong(10,collection.get(id).getHead());
        preparedStatement.setString(11,collection.get(id).getUser());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Server.log.logger.warning("Невозможно обновить элемент в БД");
        }
    }
}
