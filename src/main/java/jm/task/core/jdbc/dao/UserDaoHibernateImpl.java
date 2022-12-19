package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Transaction tr;
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.openSession()){
            tr  = session.beginTransaction();
            session.createNativeQuery("create table if not exists users" +
                    "(id INTEGER not null AUTO_INCREMENT, " +
                    "name varchar(255), " +
                    "lastName varchar(255), " +
                    "age integer, " +
                    "PRIMARY KEY (id))").executeUpdate();
                tr.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.createNativeQuery("DROP TABLE users").executeUpdate();
            tr.commit();
    }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String d = "','";
        try (Session session = sessionFactory.openSession()){
            tr = session.beginTransaction();
            session.createNativeQuery("insert into users (name, lastName, age) values(:name, :lastName, :age) ")
                    .setParameter("name", name)
                    .setParameter("lastName", lastName)
                    .setParameter("age", age)
                    .executeUpdate();
            tr.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()){
            tr = session.beginTransaction();
            session.delete(session.get(User.class, id));
            tr.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            tr = session.beginTransaction();
            users =session.createNativeQuery("select * from users", User.class)
                    .getResultList();
            tr.commit();
        }catch (Exception e ) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()){
            tr = session.beginTransaction();
            session.createNativeQuery("delete from users").executeUpdate();
            tr.commit();
    }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
