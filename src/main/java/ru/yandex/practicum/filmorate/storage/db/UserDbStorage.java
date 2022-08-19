package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.Messages;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Qualifier("UserDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final String selectAllQuery = "select ID, LOGIN, EMAIL, NAME, BIRTHDAY " +
            "from users ";
    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> get() {
        List<User> users = jdbcTemplate.query(selectAllQuery, this::mapRowToUser);
        addCollectionsToUsers(users);
        return users;
    }

    @Override
    public Optional<User> get(long id) {

        try {
            User user = jdbcTemplate.queryForObject(
                    selectAllQuery + " where ID = ?",
                    this::mapRowToUser,
                    id);
            if (user == null) {
                return Optional.empty();
            }
            addFriends(user);
            addLikes(user);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private void addFriends(User user) {
        List<Long> friends = jdbcTemplate.query("select FRIEND_ID from FRIENDS where USER_ID = ?",
                (rs, rn) -> rs.getLong("FRIEND_ID"),
                user.getId());
        user.addToFriends(friends);
    }

    private void addLikes(User user) {
        List<Long> likedFilms = jdbcTemplate.query("select FILM_ID from USERS_FILMS_LIKES where USER_ID = ?",
                (rs, rn) -> rs.getLong("FILM_ID"),
                user.getId());
        user.likeFilm(likedFilms);
    }
    @Override
    public User create(User elem) {
        String sqlQuery = "insert into users(login,email,name,birthday) " +
                "values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sqlQuery, new String[]{"id"});
            prepareStatement(statement, elem);
            return statement;
        }, keyHolder);
        elem.setId(keyHolder.getKey().longValue());
        return get(elem.getId()).orElseThrow(() -> new NoSuchElementException(Messages.NO_SUCH_USER));
    }

    @Override
    public User update(User elem) {
        String sqlQuery = "update USERS set " +
                "LOGIN = ?, EMAIL = ?, NAME = ? , BIRTHDAY = ?" +
                "where id = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sqlQuery, new String[]{"id"});
            prepareStatement(statement, elem);
            statement.setLong(5, elem.getId());
            return statement;
        });
        return get(elem.getId()).orElseThrow(() -> new NoSuchElementException(Messages.NO_SUCH_USER));
    }
    private void prepareStatement(PreparedStatement statement, User elem) throws SQLException {
        statement.setString(1, elem.getLogin());
        statement.setString(2, elem.getEmail());
        statement.setString(3, elem.getName());
        statement.setDate(4, Date.valueOf(elem.getBirthday()));
    }
    @Override
    public Set<User> getCommonFriends(long userId, long otherId) {
        String sqlQuery = "select ID, LOGIN, EMAIL, NAME, BIRTHDAY " +
                "from USERS " +
                "where ID in (" +
                    "SELECT FRIEND_ID " +
                    "from FRIENDS " +
                    "where USER_ID = ?) " +
                "and ID in (" +
                    "SELECT FRIEND_ID " +
                    "from FRIENDS " +
                    "where USER_ID = ?" +
                ")";
        Set<User> users = new HashSet<>(jdbcTemplate.query(sqlQuery, this::mapRowToUser, userId, otherId));
        addCollectionsToUsers(users);
        return users;
    }

    @Override
    public Set<User> getFriends(long id) {
        String sqlQuery = "select ID, LOGIN, EMAIL, NAME, BIRTHDAY " +
                "from USERS " +
                "where ID in (" +
                    "SELECT FRIEND_ID " +
                    "from FRIENDS " +
                    "where USER_ID = ?)";
        Set<User> users = new HashSet<>(jdbcTemplate.query(sqlQuery, this::mapRowToUser, id));
        addCollectionsToUsers(users);
        return users;
    }

    @Override
    public boolean addToFriends(long userId, long otherId) {
        String sqlQuery = "insert into FRIENDS values (?, ?)";
            return jdbcTemplate.update(sqlQuery, userId, otherId) > 0;

    }

    @Override
    public boolean deleteFromFriends(long userId, long otherId) {
        String sqlQuery = "delete from FRIENDS " +
                "where (USER_ID = ? and FRIEND_ID = ?)";
        return jdbcTemplate.update(sqlQuery, userId, otherId) > 0;
    }

    private void addCollectionsToUsers(Collection<User> users) {
        for (User user : users) {
            addFriends(user);
            addLikes(user);
        }
    }
    private User mapRowToUser(ResultSet resultSet, int rowNumber) throws SQLException {
        return new User(
                resultSet.getLong("ID"),
                resultSet.getString("EMAIL"),
                resultSet.getString("LOGIN"),
                resultSet.getString("NAME"),
                resultSet.getDate("BIRTHDAY").toLocalDate()
        );
    }
}
