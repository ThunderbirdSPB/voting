package ru.voting.repository.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import ru.voting.repository.VoteRepo;
import ru.voting.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.voting.UserTestData.*;
import static ru.voting.RestaurantTestData.*;

class VoteRepoJdbcImplTest extends AbstractRepoJdbcImplTest {
    @Autowired
    VoteRepo repo;

    @Test
    void saveNotExistingRestaurantId() {
        assertThrows(DataIntegrityViolationException.class, () -> repo.save(USER_ID, NOT_EXISTING_RESTAURANT_ID));
    }

    @Test
    void saveNotExistingUserId() {
        assertThrows(DataIntegrityViolationException.class, () -> repo.save(NOT_EXISTING_USER_ID, RESTAURANT_ID));
    }

    @Test
    void saveTwoVotesForOneUser() {
        repo.save(NOT_VOTED_USER_ID, RESTAURANT_ID);
        assertThrows(DuplicateKeyException.class, () -> repo.save(NOT_VOTED_USER_ID, RESTAURANT_ID));
    }

    @Test
    void saveWithValidData(){
        assertTrue(repo.save(NOT_VOTED_USER_ID, RESTAURANT_ID));
    }

    @Test
    void deleteNotNotVotedUserId(){
        assertThrows(NotFoundException.class, () -> repo.delete(NOT_VOTED_USER_ID, RESTAURANT_ID));
    }

    @Test
    void deleteWithValidData(){
        repo.delete(USER_ID, RESTAURANT_ID);
    }
}