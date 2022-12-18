package jm.task.core.jdbc.dao;

//import com.mysql.cj.Session;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.close();

    }

    @Override
    public void dropUsersTable() {
        Util util = new Util();
        try {
            util.getConnection().prepareStatement("DROP TABLE if exists users").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.save(user);
        tr.commit();
        session.close();

    }

    @Override
    public void removeUserById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        try {
            session.delete(session.get(User.class, id));
        } catch (IllegalArgumentException e){
            System.out.println("Usera'а с id = " + id + ", не существует.");
        }

        tr.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = (List<User>)  HibernateUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.createQuery("DELETE from User ").executeUpdate();
        tr.commit();
        session.close();
    }
}
