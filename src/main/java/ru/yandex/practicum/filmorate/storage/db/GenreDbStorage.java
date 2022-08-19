package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.EnumStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Optional;

@Repository
@Qualifier("GenreDbStorage")
public class GenreDbStorage implements EnumStorage<Film.Genre> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public EnumSet<Film.Genre> get() {
        return  EnumSet.copyOf(jdbcTemplate.query("select ID, NAME FROM GENRES", this::mapRowToElem));
    }

    @Override
    public Optional<Film.Genre> get(long id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select ID, NAME FROM GENRES where ID = ?",
                            this::mapRowToElem,
                            id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Film.Genre mapRowToElem(ResultSet resultSet, int rowNumber) throws SQLException {
        return Film.Genre.fromNumber(resultSet.getInt("ID"));
    }
}
