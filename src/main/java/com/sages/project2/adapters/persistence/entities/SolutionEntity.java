package com.sages.project2.adapters.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "solutions")
public class SolutionEntity {
    @Id
    private String login;
    private Long userId;
    private String username;
    private Long questId;
    private String solution;
    private boolean result;
}
