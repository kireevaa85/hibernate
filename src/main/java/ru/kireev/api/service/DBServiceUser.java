package ru.kireev.api.service;

import ru.kireev.api.model.User;

import java.util.Optional;

public interface DBServiceUser {

    Optional<User> getUser(long id);

    long saveUser(User user);

}
