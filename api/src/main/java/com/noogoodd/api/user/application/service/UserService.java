package com.noogoodd.api.user.application.service;

import com.noogoodd.api.user.application.dto.UserDto;
import com.noogoodd.api.user.application.port.in.GetUserUseCase;
import com.noogoodd.api.user.application.port.in.SetUserUserCase;
import com.noogoodd.api.user.application.port.out.UserRepository;
import com.noogoodd.api.user.domain.User;
import com.noogoodd.common.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements GetUserUseCase, SetUserUserCase {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserByUUID(String uuid) {
        Optional<UserEntity> userEntity = userRepository.findByUuid(uuid);
        if (userEntity.isPresent()) {
            UserEntity entity = userEntity.get();
            return new UserDto(entity.getId(), entity.getUuid(), entity.getName(), entity.getEmail());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = new User(userDto.getId(), userDto.getUuid(), userDto.getName(), userDto.getEmail());
        UserEntity userEntity = new UserEntity(user.getId(), user.getUuid(), user.getName(), user.getEmail());
        UserEntity savedUser = userRepository.save(userEntity);
        return new UserDto(savedUser.getId(), savedUser.getUuid(), savedUser.getName(), savedUser.getEmail());
    }

    @Override
    public UserDto updateUser(Long id, UserDto afterDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setName(afterDto.getName());
            userEntity.setEmail(afterDto.getEmail());
            UserEntity updatedUser = userRepository.save(userEntity);
            return new UserDto(updatedUser.getId(), updatedUser.getUuid(), updatedUser.getName(), updatedUser.getEmail());
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
