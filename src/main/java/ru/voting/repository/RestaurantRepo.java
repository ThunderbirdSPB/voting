package ru.voting.repository;

import ru.voting.model.Restaurant;

import java.util.Collection;

public interface RestaurantRepo {
    Restaurant save(Restaurant restaurant);

    boolean delete(int restaurantId);

    Collection<Restaurant> getAll();
}
