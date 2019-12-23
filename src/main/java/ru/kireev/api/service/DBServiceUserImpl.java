package ru.kireev.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kireev.api.dao.UserDao;
import ru.kireev.api.model.User;
import ru.kireev.api.sessionmanager.SessionManager;

import java.util.Optional;

public class DBServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DBServiceUserImpl.class);

    private final UserDao userDao;

    public DBServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> getUser(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var userOptional = userDao.findById(id);
                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var userId = userDao.saveUser(user);
                sessionManager.commitSession();
                logger.info("saved user with id={}", userId);
                return userId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                logger.error(e.getMessage(), e);
                throw new DBServiceException(e);
            }
        }
    }

}
