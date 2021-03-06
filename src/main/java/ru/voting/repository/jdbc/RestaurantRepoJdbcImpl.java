package ru.voting.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.RestaurantRepo;
import ru.voting.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Optional;

import static ru.voting.util.ValidationUtil.*;

@Repository
public class RestaurantRepoJdbcImpl implements RestaurantRepo {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertRestaurant;
    private static final BeanPropertyRowMapper<Restaurant> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Restaurant.class);

    private static final String DELETE_RESTAURANT = "DELETE FROM restaurant WHERE id=?";
    private static final String GET_ALL_RESTAURANTS = "SELECT * FROM restaurant";
    private static final String GET_RESTAURANT_BY_ID = "SELECT * FROM restaurant WHERE id=?";
    private static final String GET_VOTES_BY_RESTAURANT_ID = "SELECT votes FROM restaurant WHERE id=?";
    private static final String ADD_VOTE_TO_RESTAURANT = "UPDATE restaurant SET votes = votes + 1 WHERE id=?";
    private static final String DELETE_VOTE_FROM_RESTAURANT = "UPDATE restaurant SET votes = votes -1 WHERE id=?";

    public RestaurantRepoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertRestaurant = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("restaurant")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        validate(restaurant);
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("name", restaurant.getName())
                .addValue("votes", restaurant.getVotes());
        restaurant.setId(insertRestaurant.executeAndReturnKey(map).intValue());
        return restaurant;
    }

    @Override
    public void delete(int restaurantId) {
        checkNotFoundWithId(jdbcTemplate.update(DELETE_RESTAURANT, restaurantId) != 0, restaurantId);
    }

    @Override
    public Collection<Restaurant> getAll() {
        return jdbcTemplate.query(GET_ALL_RESTAURANTS, ROW_MAPPER);
    }

    @Override
    public Restaurant getById(int restaurantId) {
        Restaurant restaurant = DataAccessUtils.singleResult(jdbcTemplate.query(GET_RESTAURANT_BY_ID, ROW_MAPPER, restaurantId));
        return checkNotFoundWithId(restaurant, restaurantId);
    }

    @Override
    public void addVote(int restaurantId) {
        checkNotFoundWithId(jdbcTemplate.update(ADD_VOTE_TO_RESTAURANT, restaurantId) != 0, restaurantId);
    }

    @Override
    public void deleteVote(int restaurantId) {
        checkNotFoundWithId(jdbcTemplate.update(DELETE_VOTE_FROM_RESTAURANT, restaurantId) != 0, restaurantId);
    }

    @Override
    public int getVotes(int restaurantId) {
        return jdbcTemplate.query(GET_VOTES_BY_RESTAURANT_ID, rs -> {
            if (rs.next())
                return Optional.ofNullable(rs.getInt("votes")).orElseThrow();

            throw new NotFoundException("restaurant not found with id = " + restaurantId);
        }, restaurantId);
    }
}
