package com.noogoodd.api.home.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Home {
    private Long id;

    private String notice;

    private boolean act_flg;

    private LocalDateTime reg_dt;

    private LocalDateTime chg_dt;
}
