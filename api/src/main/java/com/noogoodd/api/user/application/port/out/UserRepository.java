package com.noogoodd.api.user.application.port.out;

import com.noogoodd.common.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends UserDetailsService {
    UserEntity save(UserEntity user);
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByUuid(String uuid);
    Optional<UserEntity> findByUsername(String username);
}
