package ru.voting.repository.jdbc;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.validation.ConstraintViolationException;

import ru.voting.model.Dish;
import ru.voting.repository.DishRepo;
import ru.voting.util.exception.NotFoundException;

import static ru.voting.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class DishRepoJdbcImplTest extends AbstractRepoJdbcImplTest{
    @Autowired
    private DishRepo repo;

    @Test
    void saveNullDish() {
        assertThrows(IllegalArgumentException.class, () -> repo.save(null, RESTAURANT_ID));
    }

    @Test
    void saveWithInvalidRestaurantId(){
        assertThrows(DataIntegrityViolationException.class, () -> repo.save(newDish, -1));
    }

    @Test
    void saveWithNegativePrice(){
        assertThrows(ConstraintViolationException.class, ()-> repo.save(dishWithNegativePrice, RESTAURANT_ID));
    }

    @Test
    void saveWithEmptyName(){
        assertThrows(ConstraintViolationException.class, ()-> repo.save(dishWithEmptyName, RESTAURANT_ID));
    }

    @Test
    void saveWithNullName(){
        assertThrows(ConstraintViolationException.class, ()-> repo.save(dishWithNullName, RESTAURANT_ID));
    }

    @Test
    void saveWithValidData(){
        Dish savedDish = repo.save(newDish, RESTAURANT_ID);
        assertEquals(savedDish, repo.getById(savedDish.getId()));
    }

    @Test
    void deleteByNotExistingDishId() {
        assertThrows(NotFoundException.class, ()-> repo.delete(NOT_EXISTING_DISH_ID));
    }

    @Test
    void deleteByExistingDishId() {
        repo.delete(DISH_ID);
        assertThrows(NotFoundException.class, ()-> repo.getById(DISH_ID));
    }

    @Test
    void getAllByExistingRestaurantId() {
        assertTrue(CollectionUtils.isEqualCollection(repo.getAllByRestaurantId(RESTAURANT_ID), dishes));
    }

    @Test
    void getAllByNotExistingRestaurantId() {
       assertTrue(repo.getAllByRestaurantId(NOT_EXISTING_RESTAURANT_ID).isEmpty());
    }

    @Test
    void getByNotExistingId(){
        assertThrows(NotFoundException.class, ()-> repo.getById(NOT_EXISTING_DISH_ID));
    }

    @Test
    void getByExistingId(){
        assertEquals(repo.getById(DISH_ID), DISH);
    }
}