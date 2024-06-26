package com.noogoodd.api.user.adapter.in.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginAuthenticationRequest {
    private String email;
    private String type;
    private String password;
}
