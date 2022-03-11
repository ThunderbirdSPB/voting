package ru.voting;

import ru.voting.model.Role;
import ru.voting.model.User;

import java.util.Set;

public class UserTestData {
    public static final int NOT_EXISTING_USER_ID = Integer.MAX_VALUE;
    public static final int USER_ID = 1;
    public static final int NOT_VOTED_USER_ID = 3;

    public static final User userWithInvalidEmail = new User(null, "null", "password", false, Set.of(Role.USER));
    public static final User userWithNullEmail = new User(null, null, "password", false, Set.of(Role.USER));
    public static final User userWithNullPassword = new User(null, "user@yandex.ru", null, false, Set.of(Role.USER));
    public static final User userWithEmptyRoles = new User(null, "user@yandex.ru", null, false, Set.of());
    public static final User newUser = new User(null, "newuser@yandex.ru", "password", false, Set.of(Role.USER));
    public static final User user = new User(USER_ID, "user2@yandex.ru", "password", false, Set.of(Role.USER));


}
