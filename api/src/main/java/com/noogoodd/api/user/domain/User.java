package com.noogoodd.api.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    private String uuid;

    private String username;

    private String password;

    private String role;

    private String name;

    private String email;

    private String gender;

    private boolean act_flg;

    private LocalDateTime reg_dt;

    private LocalDateTime chg_dt;
}
