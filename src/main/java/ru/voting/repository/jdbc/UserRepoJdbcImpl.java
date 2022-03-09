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

import java.util.List;
import java.util.Set;

@Repository
public class UserRepoJdbcImpl implements UserRepo {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUser;
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final String GET_ALL_USERS = "SELECT * FROM users WHERE id = ?";

    public UserRepoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword());
        user.setId(insertUser.executeAndReturnKey(map).intValue());
        insertRoles(user);

        return user;
    }

    @Override
    public User get(int userId) {
        List<User> users = jdbcTemplate.query(GET_ALL_USERS, ROW_MAPPER, userId);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    private void insertRoles(User user) {
        Set<Role> roles = user.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)", roles, roles.size(),
                    (ps, role) -> {
                        ps.setInt(1, user.getId());
                        ps.setString(2, role.name());
                    });
        }
    }

    private User setRoles(User user){
        if (user != null) {
            List<Role> roles = jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id=?", Role.class, user.getId());
            user.setRoles(roles);
        }
        return user;
    }
}
