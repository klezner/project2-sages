package com.sages.project2.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "solutions")
public class SolutionEntity {

    @Id
    @GeneratedValue
    private Long solutionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "quest_id")
    private QuestEntity quest;

    private String branchQuery;

    private String commitQuery;

    // enum ?
    private String result;

    private LocalDate date;
}
