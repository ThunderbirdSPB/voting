package ru.voting.repository;

public interface VoteRepo {
    boolean save(int userId, int restaurantId);

    void delete(int userId, int restaurantId);

    int getVotesCount(int restaurantId);
}
