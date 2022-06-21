package com.sages.project2.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
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
