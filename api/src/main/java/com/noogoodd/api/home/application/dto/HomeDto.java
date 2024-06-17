package com.noogoodd.api.home.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeDto {
    private Long id;

    private String notice;

    private boolean act_flg;

    private LocalDateTime reg_dt;

    private LocalDateTime chg_dt;
}
