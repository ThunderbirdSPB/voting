package ru.voting.repository;


import ru.voting.model.Dish;

import java.util.Collection;

public interface DishRepo {
    Dish save(Dish dish, int restaurantId);

    boolean delete(int dishId);

    Collection<Dish> getAllByRestaurantId(int restaurantId);
}
