package ru.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.repository.RestaurantRepo;
import ru.voting.repository.jdbc.AbstractBaseTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static ru.voting.UserTestData.*;
import static ru.voting.RestaurantTestData.*;

class VotingServiceTest extends AbstractBaseTest {
    @Autowired
    VotingService votingService;

    @Autowired
    RestaurantRepo restaurantRepo;

    @Test
    void vote(){
        int votes = restaurantRepo.getVotes(RESTAURANT_ID);
        votingService.vote(USER_ID, RESTAURANT_ID);
        assertEquals(votes + 1, restaurantRepo.getVotes(RESTAURANT_ID));
    }

    @Test
    void userVotesSameRestaurantTwice(){
        votingService.vote(USER_ID, RESTAURANT_ID);
        int votes = restaurantRepo.getVotes(RESTAURANT_ID);
        votingService.vote(USER_ID, RESTAURANT_ID);
        assertEquals(votes, restaurantRepo.getVotes(RESTAURANT_ID));
    }

    @Test
    void userVotesForTwoDifferentRestaurants(){
        votingService.vote(USER_ID, RESTAURANT_ID);
        votingService.vote(USER_ID, RESTAURANT_ID +1);

        assertEquals(0, restaurantRepo.getVotes(RESTAURANT_ID));
        assertEquals(1, restaurantRepo.getVotes(RESTAURANT_ID +1));
    }
}