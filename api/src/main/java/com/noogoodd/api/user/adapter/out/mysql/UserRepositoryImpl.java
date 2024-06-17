package com.noogoodd.api.user.adapter.out.mysql;

import com.noogoodd.api.user.adapter.out.jpa.JpaUserRepository;
import com.noogoodd.api.user.application.port.out.UserRepository;
import com.noogoodd.common.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public Optional<UserEntity> findByUuid(String uuid) {
        return jpaUserRepository.findActiveUserByUuid(uuid);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return jpaUserRepository.findActiveUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = jpaUserRepository.findActiveUserByUsername(username);
        User.UserBuilder builder;
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            builder = User.withUsername(username);
            builder.password(userEntity.getPassword());
            builder.authorities(List.of(new SimpleGrantedAuthority(userEntity.getRole())));
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }

        return builder.build();
    }
}
