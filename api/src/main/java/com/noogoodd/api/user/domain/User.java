package com.noogoodd.api.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private String role;
    private String uuid;
    private String nickname;
    private boolean disability_yn;
    private String disability_type;
    private String aid_type;
    private String address_area;
    private String gender;
    private String birth_day;
    private String sign_type;
    private boolean act_flg;
    private LocalDateTime reg_dt;
    private LocalDateTime chg_dt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));

    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
