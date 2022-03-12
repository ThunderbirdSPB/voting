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

class VoteRepoJdbcImplTest extends AbstractBaseTest {
    @Autowired
    VoteRepo repo;

    @Test
    void saveNotExistingRestaurantId() {
        assertThrows(DataIntegrityViolationException.class, () -> repo.addVote(USER_ID, NOT_EXISTING_RESTAURANT_ID));
    }

    @Test
    void saveNotExistingUserId() {
        assertThrows(DataIntegrityViolationException.class, () -> repo.addVote(NOT_EXISTING_USER_ID, RESTAURANT_ID));
    }

    @Test
    void saveTwoVotesForOneUser() {
        repo.addVote(NOT_VOTED_USER_ID, RESTAURANT_ID);
        assertThrows(DuplicateKeyException.class, () -> repo.addVote(NOT_VOTED_USER_ID, RESTAURANT_ID));
    }

    @Test
    void saveWithValidData(){
        assertTrue(repo.addVote(NOT_VOTED_USER_ID, RESTAURANT_ID));
    }

    @Test
    void deleteNotNotVotedUserId(){
        assertThrows(NotFoundException.class, () -> repo.deleteVote(NOT_VOTED_USER_ID, RESTAURANT_ID));
    }

    @Test
    void deleteWithValidData(){
        repo.deleteVote(USER_ID, RESTAURANT_ID);
    }

    @Test
    void getRestaurantIdByUserId(){
        assertEquals(RESTAURANT_ID, repo.getRestaurantIdByUserId(USER_ID));
    }
}