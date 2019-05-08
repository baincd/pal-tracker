package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate njdbcTemplate;

    public JdbcTimeEntryRepository(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        njdbcTemplate = new NamedParameterJdbcTemplate(ds);
    }

    public TimeEntry createORIG(TimeEntry timeEntry) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                            "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        njdbcTemplate.update(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                            "VALUES (:project_id, :userId, :dt, :hrs)",
                    new MapSqlParameterSource()
                            .addValue("project_id", timeEntry.getProjectId())
                            .addValue("userId", timeEntry.getUserId())
                            .addValue("dt", Date.valueOf(timeEntry.getDate()))
                            .addValue("hrs", timeEntry.getHours()),
                generatedKeyHolder
            );


        return find(generatedKeyHolder.getKey().longValue());
    }


    @Override
    public TimeEntry find(Long id) {
        return jdbcTemplate.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
                new Object[]{id},
                extractor);
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query("select * from time_entries", mapper);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        jdbcTemplate.update("update time_entries set project_id = ?, user_id = ?, date = ?, hours = ? where id = ?",
            timeEntry.getProjectId(), timeEntry.getUserId(), Date.valueOf(timeEntry.getDate()), timeEntry.getHours(), id);

        return find(id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from time_entries where id = ?", id);
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
