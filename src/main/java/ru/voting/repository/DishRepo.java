package ru.voting.repository;


import ru.voting.model.Dish;

import java.util.Collection;

public interface DishRepo {
    Dish save(Dish dish, int restaurantId);

    void delete(int dishId);

    Collection<Dish> getAllByRestaurantId(int restaurantId);

    Dish getById(int dishId);
}
