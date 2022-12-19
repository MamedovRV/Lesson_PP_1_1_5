package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    private Util util;

    public UserDaoJDBCImpl() {
        util = new Util();
        connection = util.getConnection();
    }

    private void StUpdate(String sql){
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createUsersTable() {
        String sql = "create table if not exists users" +
                "(idUsers INTEGER not null AUTO_INCREMENT, " +
                "name varchar(255), " +
                "lastName varchar(255), " +
                "age integer, " +
                "PRIMARY KEY (idUsers))";

        StUpdate(sql);
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE if exists users" ;

        StUpdate(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        String d = "','";
        String sql = "insert into users (name, lastName, age) " +
                "values ('" + name + d + lastName + d + String.valueOf(age)+ "')";
        StUpdate(sql);
    }

    public void removeUserById(long id) {
        String sql = "delete from users" +
                " where idUsers = " +
                id;

        StUpdate(sql);
    }

    public List<User> getAllUsers() {
        String quary = "select * from users";
        List<User> result = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(quary);
            ResultSet resultSet = preparedStatement.executeQuery(quary);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("idUsers"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));

                result.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void cleanUsersTable() {
        String sql = "delete from users";

        StUpdate(sql);
    }
}
