package ru.voting.repository;


import ru.voting.model.Dish;

public interface DishRepo {
    Dish save(Dish dish);

    Dish delete(int dishId);
}
