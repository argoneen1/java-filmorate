package ru.yandex.practicum.filmorate.storage;

import java.util.EnumSet;
import java.util.Optional;

public interface EnumStorage <T extends Enum<T>>{
    EnumSet<T> get();
    Optional<T> get(long id);
}
