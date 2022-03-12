package ru.voting.repository;

public interface VoteRepo {
    boolean addVote(int userId, int restaurantId);

    void deleteVote(int userId, int restaurantId);

    int getRestaurantIdByUserId(int userId);
}
