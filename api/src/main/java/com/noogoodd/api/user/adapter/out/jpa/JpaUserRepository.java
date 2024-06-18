package com.noogoodd.api.user.adapter.out.jpa;

import com.noogoodd.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.sign_type = :sign_type AND u.act_flg = true")
    Optional<UserEntity> findActiveUserByEmailAndType(String email, String sign_type);
}
