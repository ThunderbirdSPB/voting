package ru.voting.repository.jdbc;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.ConstraintViolationException;

import ru.voting.model.Restaurant;
import ru.voting.repository.RestaurantRepo;
import ru.voting.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.voting.RestaurantTestData.*;

class RestaurantRepoJdbcImplTest extends AbstractBaseTest {
    @Autowired
    RestaurantRepo repo;

    @Test
    void saveNullRestaurant(){
        assertThrows(IllegalArgumentException.class, () -> repo.save(null));
    }

    @Test
    void saveWithNullName(){
        assertThrows(ConstraintViolationException.class, () -> repo.save(restaurantWithNullName));
    }

    @Test
    void saveWithNegativeVotes(){
        assertThrows(ConstraintViolationException.class, () -> repo.save(restaurantWithNegativeVotes));
    }

    @Test
    void saveWithValidData(){
        Restaurant savedRestaurant = repo.save(newRestaurant);
        assertEquals(savedRestaurant, repo.getById(savedRestaurant.getId()));
    }

    @Test
    void getByNotExistingId(){
        assertThrows(NotFoundException.class, ()-> repo.getById(NOT_EXISTING_RESTAURANT_ID));
    }

    @Test
    void getByExistingId(){
        assertEquals(repo.getById(RESTAURANT_ID), RESTAURANT);
    }

    @Test
    void getAll(){
        assertTrue(CollectionUtils.isEqualCollection(repo.getAll(), restaurants));
    }

    @Test
    void deleteByNotExistingId() {
        assertThrows(NotFoundException.class, ()-> repo.delete(NOT_EXISTING_RESTAURANT_ID));
    }

    @Test
    void deleteByExistingId() {
        repo.delete(RESTAURANT_ID);
        assertThrows(NotFoundException.class, ()-> repo.getById(RESTAURANT_ID));
    }

    @Test
    void addVoteToRestaurant(){
        int votes = repo.getVotes(RESTAURANT_ID);
        repo.addVote(RESTAURANT_ID);
        assertEquals( 1, repo.getVotes(RESTAURANT_ID) - votes);
    }

    @Test
    void addVoteToNotExistingRestaurant(){
        assertThrows(NotFoundException.class, () -> repo.addVote(NOT_EXISTING_RESTAURANT_ID));
    }

    @Test
    void deleteVoteFromRestaurant(){
        int votes = repo.getVotes(RESTAURANT_ID);
        repo.deleteVote(RESTAURANT_ID);
        assertEquals( 1, votes - repo.getVotes(RESTAURANT_ID));
    }

    @Test
    void deleteVoteFromNotExistingRestaurant(){
        assertThrows(NotFoundException.class, () -> repo.deleteVote(NOT_EXISTING_RESTAURANT_ID));
    }

    @Test
    void getVotesForRestaurant(){
        assertEquals(VOTES, repo.getVotes(RESTAURANT_ID));
    }

    @Test
    void getVotesForNotExistingRestaurant(){
        assertThrows(NotFoundException.class, ()-> repo.getVotes(NOT_EXISTING_RESTAURANT_ID));
    }
}