package ru.voting.service;

import org.springframework.stereotype.Service;
import ru.voting.repository.RestaurantRepo;
import ru.voting.repository.UserRepo;
import ru.voting.repository.VoteRepo;

import java.time.LocalTime;

@Service
public class VotingService {
    private final UserRepo userRepo;
    private final RestaurantRepo restaurantRepo;
    private final VoteRepo voteRepo;

    public VotingService(UserRepo userRepo, RestaurantRepo restaurantRepo, VoteRepo voteRepo) {
        this.userRepo = userRepo;
        this.restaurantRepo = restaurantRepo;
        this.voteRepo = voteRepo;
    }

    public void vote(int userId, int restaurantId){
        if (userRepo.isUserVoted(userId))
            revote(userId, restaurantId);
        else
            doVote(userId, restaurantId);
    }

    private void doVote(int userId, int restaurantId){
        userRepo.setUserVoted(userId, true);
        restaurantRepo.addVote(restaurantId);
        voteRepo.addVote(userId, restaurantId);
    }

    // If user votes after 11:00 then it is too late, vote can't be changed
    private void revote(int userId, int restaurantId){
        if (LocalTime.now().getHour() > 11)
            return;

        int oldRestaurantId = voteRepo.getRestaurantIdByUserId(userId);
        restaurantRepo.deleteVote(oldRestaurantId);
        voteRepo.deleteVote(userId, oldRestaurantId);
        voteRepo.addVote(userId, restaurantId);
        restaurantRepo.addVote(restaurantId);
    }
}
