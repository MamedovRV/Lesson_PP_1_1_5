package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.openSession()){
           Transaction  tr  = session.beginTransaction();
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
            Transaction tr = session.beginTransaction();
            session.createNativeQuery("DROP TABLE users").executeUpdate();
            tr.commit();
    }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()){
           Transaction  tr = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tr.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()){
            Transaction tr = session.beginTransaction();
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
            Transaction tr = session.beginTransaction();
            users =session.createQuery("from User")
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
            Transaction tr = session.beginTransaction();
            session.createNativeQuery("delete from users").executeUpdate();
            tr.commit();
    }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
