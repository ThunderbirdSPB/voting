package ru.voting.repository.jdbc;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.voting.config.DBConfig;

@SpringJUnitConfig(classes = DBConfig.class)
@Sql(scripts = {"classpath:initDB.sql", "classpath:populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class AbstractRepoJdbcImplTest {}
