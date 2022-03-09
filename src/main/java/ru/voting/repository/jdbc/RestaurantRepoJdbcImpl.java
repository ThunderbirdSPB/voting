package ru.voting.repository.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.RestaurantRepo;

import java.util.Collection;

@Repository
public class RestaurantRepoJdbcImpl implements RestaurantRepo {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertRestaurant;
    private static final BeanPropertyRowMapper<Restaurant> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Restaurant.class);

    private static final String DELETE_RESTAURANT = "DELETE FROM restaurant WHERE id=?";
    private static final String GET_ALL_RESTAURANTS = "SELECT * FROM restaurant";

    public RestaurantRepoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertRestaurant = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("restaurant")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", restaurant.getName())
                .addValue("votes", restaurant.getVotes());
        restaurant.setId(insertRestaurant.executeAndReturnKey(map).intValue());
        return restaurant;
    }

    @Override
    public boolean delete(int restaurantId) {
        return jdbcTemplate.update(DELETE_RESTAURANT, restaurantId) != 0;
    }

    @Override
    public Collection<Restaurant> getAll() {
        return jdbcTemplate.query(GET_ALL_RESTAURANTS, ROW_MAPPER);
    }
}
