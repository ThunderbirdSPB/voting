package ru.voting.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.voting.model.Role;
import ru.voting.model.User;
import ru.voting.repository.UserRepo;
import ru.voting.util.exception.NotFoundException;

import java.util.List;
import java.util.Set;

import static ru.voting.util.ValidationUtil.*;

@Repository
public class UserRepoJdbcImpl implements UserRepo {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUser;
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String IS_USER_VOTED = "SELECT voted FROM users WHERE id = ?";
    private static final String SET_USER_VOTED = "UPDATE users SET voted = ? WHERE id = ?";
    private static final String INSERT_USER_ROLES = "INSERT INTO user_roles (user_id, role) VALUES (?, ?)";
    private static final String GET_ROLE_BY_ID = "SELECT role FROM user_roles WHERE user_id=?";

    public UserRepoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        validate(user);
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("voted", user.isVoted())
                .addValue("password", user.getPassword());
        user.setId(insertUser.executeAndReturnKey(map).intValue());
        insertRoles(user);

        return user;
    }

    @Override
    public User get(int userId) {
        User user = DataAccessUtils.singleResult(jdbcTemplate.query(GET_USER_BY_ID, ROW_MAPPER, userId));
        setRoles(user);
        return checkNotFoundWithId(user, userId);
    }

    @Override
    public boolean isUserVoted(int userId) {
        return Boolean.TRUE.equals(jdbcTemplate.query(IS_USER_VOTED, rs -> {
            if (rs.next())
                return rs.getBoolean("voted");
            throw new NotFoundException("not found user with id = " + userId);
        }, userId));
    }

    @Override
    public void setUserVoted(int userId, boolean voted) {
        checkNotFoundWithId(jdbcTemplate.update(SET_USER_VOTED, voted, userId) != 0, userId);
    }

    private void insertRoles(User user) {
        Set<Role> roles = user.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            jdbcTemplate.batchUpdate(INSERT_USER_ROLES, roles, roles.size(),
                    (ps, role) -> {
                        ps.setInt(1, user.getId());
                        ps.setString(2, role.name());
                    });
        }
    }

    private User setRoles(User user){
        if (user != null) {
            List<Role> roles = jdbcTemplate.queryForList(GET_ROLE_BY_ID, Role.class, user.getId());
            user.setRoles(roles);
        }
        return user;
    }
}
