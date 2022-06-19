package com.sages.project2.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Solution {

    Long userId;
    String username;
    Long questId;
    String solution;
    boolean result;

}
