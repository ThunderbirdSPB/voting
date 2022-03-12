package ru.voting.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.voting.repository.VoteRepo;
import ru.voting.util.exception.NotFoundException;
import java.util.Optional;

import static ru.voting.util.ValidationUtil.*;

@Repository
public class VoteRepoJdbcImpl implements VoteRepo {
    private final JdbcTemplate jdbcTemplate;
    private final static String SAVE_VOTE = "INSERT INTO vote (user_id, restaurant_id) VALUES (?,?)";
    private final static String DELETE_VOTE = "DELETE FROM vote WHERE user_id = ? AND restaurant_id = ?";
    private final static String GET_RESTAURANT_ID_BY_USER_ID = "SELECT restaurant_id FROM vote WHERE user_id = ?";

    public VoteRepoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public boolean addVote(int userId, int restaurantId) {
        return jdbcTemplate.update(SAVE_VOTE, userId, restaurantId) != 0;
    }

    @Override
    public void deleteVote(int userId, int restaurantId) {
        checkNotFound(jdbcTemplate.update(DELETE_VOTE, userId, restaurantId) != 0, "Wrong userId or restaurantId");
    }

    @Override
    public int getRestaurantIdByUserId(int userId) {
        return jdbcTemplate.query(GET_RESTAURANT_ID_BY_USER_ID, rs -> {
            if (rs.next())
                return Optional.ofNullable(rs.getInt("restaurant_id")).orElseThrow();
            throw new NotFoundException("user didn't vote yet, userId= " + userId);
        }, userId);
    }
}
