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
    private String email;
    private String password;
    private String role;
    private String uuid;
    private String nickname;
    private String disability_type;
    private String aid_type;
    private String address_area;
    private String gender;
    private String birth_day;
    private String sign_type;
    private boolean act_flg;
    private LocalDateTime reg_dt;
    private LocalDateTime chg_dt;
}
