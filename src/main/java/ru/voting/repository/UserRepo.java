package ru.voting.repository;

import ru.voting.model.User;

public interface UserRepo {
    User save(User user);

    User get(int userId);
}
