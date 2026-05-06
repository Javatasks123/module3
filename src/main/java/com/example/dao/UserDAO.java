package com.example.dao;

import com.example.model.User;
import com.example.util.HibernateUtil;
import org.hibernate.Session;

public class UserDAO {

    public void register(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        session.close();
    }

    public User findByLoginAndPassword(String login, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        User user = session.createQuery(
                        "FROM User WHERE login = :login AND password = :password",
                        User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult();

        session.close();
        return user;
    }
}