package ru.voting.repository.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepo;

import java.util.Collection;

@Repository
public class DishRepoJdbcImpl implements DishRepo {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertDish;
    private static final BeanPropertyRowMapper<Dish> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Dish.class);

    private static final String DELETE_BY_ID = "DELETE FROM dish WHERE id=?";
    private static final String GET_ALL_BY_RESTAURANT_ID = "SELECT * FROM dish WHERE restaurant_id=?";

    public DishRepoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertDish = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("dish")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Dish save(Dish dish, int restaurantId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", dish.getName())
                .addValue("price", dish.getPrice())
                .addValue("restaurant_id", restaurantId);
        dish.setId(insertDish.executeAndReturnKey(map).intValue());
        return dish;
    }

    @Override
    public boolean delete(int dishId) {
        return jdbcTemplate.update(DELETE_BY_ID, dishId) != 0;
    }

    @Override
    public Collection<Dish> getAllByRestaurantId(int restaurantId){
        return jdbcTemplate.query(GET_ALL_BY_RESTAURANT_ID, ROW_MAPPER, restaurantId);
    }
}
