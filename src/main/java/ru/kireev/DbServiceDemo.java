package ru.kireev;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kireev.api.dao.UserDao;
import ru.kireev.api.model.User;
import ru.kireev.api.service.DBServiceUser;
import ru.kireev.api.service.DBServiceUserImpl;
import ru.kireev.hibernate.HibernateUtils;
import ru.kireev.hibernate.dao.UserDaoHibernate;
import ru.kireev.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        // Все главное см в тестах
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DBServiceUserImpl(userDao);


        long id = dbServiceUser.saveUser(new User(0, "Вася"));
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        id = dbServiceUser.saveUser(new User(1L, "А! Нет. Это же совсем не Вася"));
        Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);

        outputUserOptional("Created user", mayBeCreatedUser);
        outputUserOptional("Updated user", mayBeUpdatedUser);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }

}
