package com.noogoodd.api.user.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String uuid;

    private String name;

    private String email;

    private String gender;

    private boolean act_flg;

    private LocalDateTime reg_dt;

    private LocalDateTime chg_dt;
}
