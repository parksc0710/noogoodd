package com.noogoodd.api.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private Long id;

    private String uuid;

    private String name;

    private String email;
}
