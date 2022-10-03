package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() {
        Connection connection = null;
        try {
            connection = Util.getInstance().getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE if not exists USERS" +
                    "(ID tinyint AUTO_INCREMENT PRIMARY KEY  not null ," +
                    "NAME varchar(45) not null ," +
                    "LASTNAME varchar(45) not null ," +
                    "AGE int)";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = null;
        try {
            connection = Util.getInstance().getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE if  exists USERS");
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        try {
            connection = Util.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO USERS" +
                    " (NAME,LASTNAME,AGE) VALUES (?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeUserById(long id) {
        Connection connection = null;
        try {
            connection = Util.getInstance().getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE from USERS  where id = " + id);
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
    @Override
    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        connection = Util.getInstance().getConnection();
        String sql = ("SELECT * FROM  USERS");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        List<User> users = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery(sql);
        while (resultSet.next()) {
            var user = new User(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getByte(4));
            users.add(user);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Connection connection = null;
        try {
            connection = Util.getInstance().getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE USERS");
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}

