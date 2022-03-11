package ru.voting.repository.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.voting.repository.VoteRepo;

import static ru.voting.util.ValidationUtil.*;

@Repository
public class VoteRepoJdbcImpl implements VoteRepo {
    private final JdbcTemplate jdbcTemplate;
    private final static String SAVE_VOTE = "INSERT INTO vote (user_id, restaurant_id) VALUES (?,?)";
    private final static String DELETE_VOTE = "DELETE FROM vote WHERE user_id = ? AND restaurant_id = ?";
    private final static String GET_VOTES_COUNT = "SELECT COUNT(restaurant_id) FROM vote WHERE restaurant_id = ?";

    public VoteRepoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public boolean save(int userId, int restaurantId) {
        return jdbcTemplate.update(SAVE_VOTE, userId, restaurantId) != 0;
    }

    @Override
    public void delete(int userId, int restaurantId) {
        checkNotFound(jdbcTemplate.update(DELETE_VOTE, userId, restaurantId) != 0, "Wrong userId or restaurantId");
    }

    @Override
    public int getVotesCount(int restaurantId) {
        return jdbcTemplate.queryForObject(GET_VOTES_COUNT, new BeanPropertyRowMapper<>(Integer.class), restaurantId);
    }
}
