package com.sages.project2.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "quests")
public class QuestEntity {

    @Id
    @GeneratedValue
    private Long questId;

    private String repoUrl;

    private String questContent;

    @OneToMany
    private List<UserEntity> users;

}
