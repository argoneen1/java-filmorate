package ru.yandex.practicum.filmorate.model.exceptions;

public class Messages {
    public static final String NO_SUCH_FILM = "There is no such film";
    public static final String NO_SUCH_USER = "There is no such user";
    public static final String ILLEGAL_FIELDS_STATE = "There is no such user";
    public static <T> String getNoSuchElemMessage(long id, Class<T> clazz) {
        return "There is no such " + clazz.getName().toLowerCase() + " with " + id;
    }
    public static String getNoSuchElemMessage(long id) {
        return "There is no such element with " + id;
    }
}
