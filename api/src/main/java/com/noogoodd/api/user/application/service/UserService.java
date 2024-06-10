package com.noogoodd.api.user.application.service;

import com.noogoodd.api.user.application.dto.UserDto;
import com.noogoodd.api.user.application.port.in.GetUserUseCase;
import com.noogoodd.api.user.application.port.in.SetUserUserCase;
import com.noogoodd.api.user.application.port.out.UserRepository;
import com.noogoodd.api.user.domain.User;
import com.noogoodd.common.entity.UserEntity;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements GetUserUseCase, SetUserUserCase {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            UserEntity entity = userEntity.get();
            return new UserDto(entity.getId(), entity.getUuid(), entity.getUsername(), entity.getPassword(), entity.getRole(), entity.getName(), entity.getEmail(), entity.getGender(), entity.isAct_flg(), entity.getReg_dt(), entity.getChg_dt());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserDto getUserByUUID(String uuid) {
        Optional<UserEntity> userEntity = userRepository.findByUuid(uuid);
        if (userEntity.isPresent()) {
            UserEntity entity = userEntity.get();
            return new UserDto(entity.getId(), entity.getUuid(), entity.getUsername(), entity.getPassword(), entity.getRole(), entity.getName(), entity.getEmail(), entity.getGender(), entity.isAct_flg(), entity.getReg_dt(), entity.getChg_dt());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserDto getUserByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            UserEntity entity = userEntity.get();
            return new UserDto(entity.getId(), entity.getUuid(), entity.getUsername(), entity.getPassword(), entity.getRole(), entity.getName(), entity.getEmail(), entity.getGender(), entity.isAct_flg(), entity.getReg_dt(), entity.getChg_dt());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = new User(userDto.getId(), userDto.getUuid(), userDto.getUsername(), userDto.getPassword(), userDto.getRole(), userDto.getName(), userDto.getEmail(), userDto.getGender(), userDto.isAct_flg(), userDto.getReg_dt(), userDto.getChg_dt());
        UserEntity userEntity = new UserEntity(user.getId(), user.getUuid(), user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getRole(), user.getName(), user.getEmail(), user.getGender(), user.isAct_flg(), user.getReg_dt(), user.getChg_dt());
        UserEntity savedUser = userRepository.save(userEntity);
        return new UserDto(savedUser.getId(), savedUser.getUuid(), savedUser.getUsername(), savedUser.getPassword(), savedUser.getRole(), savedUser.getName(), savedUser.getEmail(), savedUser.getGender(), savedUser.isAct_flg(), savedUser.getReg_dt(), savedUser.getChg_dt());
    }

    @Override
    public UserDto updateUser(Long id, UserDto afterDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if(StringUtils.isNotBlank(afterDto.getPassword())) {
                userEntity.setPassword(passwordEncoder.encode(afterDto.getPassword()));
            }
            userEntity.setName(afterDto.getName());
            userEntity.setEmail(afterDto.getEmail());
            userEntity.setGender(afterDto.getGender());
            userEntity.setAct_flg(afterDto.isAct_flg());
            UserEntity updatedUser = userRepository.save(userEntity);
            return new UserDto(updatedUser.getId(), updatedUser.getUuid(), updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getRole(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getGender(), updatedUser.isAct_flg(), updatedUser.getReg_dt(), updatedUser.getChg_dt());
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
