package ru.voting.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepo;

import java.util.Collection;

import static ru.voting.util.ValidationUtil.*;

@Repository
public class DishRepoJdbcImpl implements DishRepo {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertDish;
    private static final BeanPropertyRowMapper<Dish> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Dish.class);

    private static final String DELETE_BY_ID = "DELETE FROM dish WHERE id=?";
    private static final String GET_ALL_BY_RESTAURANT_ID = "SELECT * FROM dish WHERE restaurant_id=?";
    private static final String GET_BY_DISH_ID = "SELECT * FROM dish WHERE id=?";

    public DishRepoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertDish = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("dish")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Dish save(Dish dish, int restaurantId) {
        validate(dish);
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", dish.getName())
                .addValue("price", dish.getPrice())
                .addValue("restaurant_id", restaurantId);
        dish.setId(insertDish.executeAndReturnKey(map).intValue());
        return dish;
    }

    @Override
    public void delete(int dishId) {
        checkNotFoundWithId(jdbcTemplate.update(DELETE_BY_ID, dishId) != 0, dishId);
    }

    @Override
    public Collection<Dish> getAllByRestaurantId(int restaurantId){
        return jdbcTemplate.query(GET_ALL_BY_RESTAURANT_ID, ROW_MAPPER, restaurantId);
    }

    @Override
    public Dish getById(int dishId) {
        Dish dish = DataAccessUtils.singleResult(jdbcTemplate.query(GET_BY_DISH_ID, ROW_MAPPER, dishId));
        return checkNotFoundWithId(dish, dishId);
    }
}
