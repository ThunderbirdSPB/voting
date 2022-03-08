package ru.voting.repository;

public interface VoteRepo {
    boolean save(int userId, int restaurantId);

    boolean delete(int userId, int restaurantId);
}
