package com.noogoodd.api.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SNSLoginResponse {
    private String uuid;
    private String email;
    private String type;
}
