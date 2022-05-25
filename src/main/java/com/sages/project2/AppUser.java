package com.sages.project2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class AppUser {

    @Id
    @GeneratedValue
    private int id;

    private String login;

    private String Role;

}
