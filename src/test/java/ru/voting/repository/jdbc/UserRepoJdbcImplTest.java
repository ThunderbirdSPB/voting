package ru.voting.repository.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.ConstraintViolationException;

import ru.voting.model.User;
import ru.voting.repository.UserRepo;
import ru.voting.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.voting.UserTestData.*;

class UserRepoJdbcImplTest extends AbstractRepoJdbcImplTest{
    @Autowired
    UserRepo repo;

    @Test
    void saveNullUser(){
        assertThrows(IllegalArgumentException.class, () -> repo.save(null));
    }

    @Test
    void saveWithNullEmail(){
        assertThrows(ConstraintViolationException.class, () -> repo.save(userWithNullEmail));
    }

    @Test
    void saveWithNullPassword(){
        assertThrows(ConstraintViolationException.class, () -> repo.save(userWithNullPassword));
    }

    @Test
    void saveNotValidEmail(){
        assertThrows(ConstraintViolationException.class, () -> repo.save(userWithInvalidEmail));
    }

    @Test
    void saveWithEmptyRoles(){
        assertThrows(ConstraintViolationException.class, () -> repo.save(userWithEmptyRoles));
    }

    @Test
    void saveWithValidData(){
        User savedUser = repo.save(newUser);
        assertEquals(savedUser, repo.get(savedUser.getId()));
    }

    @Test
    void getByNotExistingId(){
        assertThrows(NotFoundException.class, ()-> repo.get(NOT_EXISTING_USER_ID));
    }

    @Test
    void getByExistingId(){
        assertEquals(repo.get(USER_ID), user);
    }
}