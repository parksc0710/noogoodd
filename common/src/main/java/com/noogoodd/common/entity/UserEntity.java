package com.noogoodd.common.entity;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private String password;

    @Column(nullable = false)
    private String role;

    @Column(unique = true)
    private String uuid;

    @Column(nullable = false)
    private String nickname;

    private String disability_type;

    private String aid_type;

    private String address_area;

    private String gender;

    private String birth_day;

    @Column(nullable = false)
    private String sign_type;

    private boolean act_flg;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime reg_dt;

    @LastModifiedDate
    private LocalDateTime chg_dt;

}