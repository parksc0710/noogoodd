package com.noogoodd.api.user.adapter.out.mysql;

import com.noogoodd.api.user.adapter.out.jpa.JpaUserRepository;
import com.noogoodd.api.user.application.port.out.UserRepository;
import com.noogoodd.common.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public UserEntity save(UserEntity user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByEmailAndType(String email, String type) {
        return jpaUserRepository.findActiveUserByEmailAndType(email, type);
    }
}
