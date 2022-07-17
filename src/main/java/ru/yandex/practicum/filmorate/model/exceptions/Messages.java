package ru.yandex.practicum.filmorate.model.exceptions;

public class Messages {
    public static final String NO_SUCH_FILM = "There is no such film";
    public static final String NO_SUCH_USER = "There is no such user";

    public static String getNoSuchElemMessage(long id, String elemTypeName) {
        return "There is no such " + elemTypeName.toLowerCase() + " with " + id;
    }
}
