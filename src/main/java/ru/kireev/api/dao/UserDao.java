package ru.kireev.api.dao;

import ru.kireev.api.model.User;
import ru.kireev.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    long saveUser(User user);

    SessionManager getSessionManager();

}
