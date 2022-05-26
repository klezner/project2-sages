package com.sages.project2.adapters.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @GeneratedValue
    @Id
    protected Long id;

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }
        BaseEntity otherEntity = (BaseEntity) otherObject;
        return this.id != null && Objects.equals(id, otherEntity.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
