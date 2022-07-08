package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public abstract class BaseModel {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int id;

    protected BaseModel() {
    }
}
