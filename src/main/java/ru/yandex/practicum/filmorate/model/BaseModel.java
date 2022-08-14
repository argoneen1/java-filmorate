package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@EqualsAndHashCode
@ToString
public abstract class BaseModel {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    protected BaseModel() {
    }

    public static String getElemName() {
        throw new RuntimeException();
    }

    public abstract void setPreviousIdGeneratorValue();
}
