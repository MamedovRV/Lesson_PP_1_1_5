package jm.task.core.jdbc;




import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();

        userService.createUsersTable();
        userService.saveUser("Petr", "Vasilev", (byte)97);
        userService.saveUser("Vasiliy", "Vasilev", (byte)78);
        userService.saveUser("James", "Vasilev", (byte)9);
        userService.saveUser("Anna", "Vasileva", (byte)108);

        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
