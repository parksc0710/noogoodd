package com.noogoodd.api.user.adapter.out.jpa;

import com.noogoodd.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.uuid = :uuid AND u.act_flg = true")
    Optional<UserEntity> findActiveUserByUuid(String uuid);
}
