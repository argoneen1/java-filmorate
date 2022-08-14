package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.Messages;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.*;

@Repository
@Qualifier("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private static final String selectAllQuery =
            "select ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_RATING_ID" +
                    " from FILMS";

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> get() {
        try {
            List<Film> films = jdbcTemplate.query(selectAllQuery, this::mapRowToFilm);
            addCollectionsToFilms(films);
            return films;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Film> get(long id) {
        try {
            Film film = jdbcTemplate.queryForObject(
                    selectAllQuery + " where ID = ?",
                    this::mapRowToFilm,
                    id);
            if (film == null) {
                return Optional.empty();
            }
            addGenres(film);
            return Optional.of(film);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Film create(Film elem) {
        String sqlQuery = "insert into FILMS(NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_RATING_ID) " +
                "values(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sqlQuery, new String[]{"id"});
            prepareStatement(statement, elem);
            return statement;
        },keyHolder);
        elem.setId(keyHolder.getKey().longValue());
        return renewGenresSetAndReturnFromDatabase(elem);
    }



    @Override
    public Film update(Film elem) {
        String sqlQuery = "UPDATE FILMS set " +
                "NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA_RATING_ID = ? " +
                "where id = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sqlQuery, new String[]{"id"});
            prepareStatement(statement, elem);
            statement.setLong(6, elem.getId());
            return statement;
        });
        return renewGenresSetAndReturnFromDatabase(elem);
    }

    private Film renewGenresSetAndReturnFromDatabase(Film elem) {
        if (elem.getGenres() == null) {
            elem.setGenres(EnumSet.noneOf(Film.Genre.class));
        }
        jdbcTemplate.update("delete from FILMS_GENRES where FILM_ID = ?", elem.getId());
        for (Film.Genre genre : elem.getGenres()) {
            jdbcTemplate.update("insert into FILMS_GENRES VALUES ( ?, ? )", elem.getId(), genre.getId());
        }
        return get(elem.getId()).orElseThrow(() -> new NoSuchElementException(
                Messages.getNoSuchElemMessage(elem.getId(), Film.class)));
    }

    private void prepareStatement(PreparedStatement statement, Film elem) throws SQLException {
        statement.setString(1, elem.getName());
        statement.setString(2, elem.getDescription());
        statement.setDate(3, Date.valueOf(elem.getReleaseDate()));
        statement.setLong(4, elem.getDuration().toMinutes());
        statement.setLong(5, elem.getMpaRating().getId());
    }
    @Override
    public List<Film> getMostLiked(int count) {
        String sqlQuery = "select ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_RATING_ID/*, COUNT(USER_ID)*/" +
                " from FILMS" +
                " LEFT JOIN USERS_FILMS_LIKES UFL on FILMS.ID = UFL.FILM_ID " +
                "GROUP BY ID " +
                "order by COUNT(USER_ID) DESC " +
                "limit ?";
        List<Film> mostLiked = jdbcTemplate.query(sqlQuery, this::mapRowToFilm, count);
        addCollectionsToFilms(mostLiked);
        return mostLiked;
    }

    @Override
    public boolean setLike(long filmId, long userId) {
        return jdbcTemplate.update("insert into USERS_FILMS_LIKES VALUES ( ?, ? )", userId, filmId) > 0;
    }

    @Override
    public boolean deleteLike(long filmId, long userId) {
        return jdbcTemplate.update("DELETE FROM USERS_FILMS_LIKES " +
                "where FILM_ID = ? " +
                "and USER_ID = ?",
                filmId, userId) > 0;
    }

    private void addGenres(Film film) {
        EnumSet<Film.Genre> genres;
        try {
            genres = EnumSet.<Film.Genre>copyOf(
                    jdbcTemplate.query("select GENRE_ID from FILMS_GENRES where FILM_ID = ?",
                            (rs, rowNum) -> Film.Genre.fromNumber(rs.getInt("GENRE_ID")),
                            film.getId())
            );
        } catch (IllegalArgumentException e) {
            genres = EnumSet.noneOf(Film.Genre.class);
        }
        film.addGenre(genres);
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Film(
                resultSet.getLong("ID"),
                resultSet.getString("NAME"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getDate("RELEASE_DATE").toLocalDate(),
                Duration.ofMinutes(resultSet.getInt("DURATION")),
                Film.MPARating.fromNumber(resultSet.getInt("MPA_RATING_ID"))
        );
    }

    private void addCollectionsToFilms(Collection<Film> films) {
        for (Film film : films) {
            addGenres(film);
        }
    }
}
