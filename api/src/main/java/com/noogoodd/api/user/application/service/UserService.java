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
            return new UserDto(entity.getId(), entity.getEmail(), entity.getPassword(), entity.getRole(), entity.getUuid(), entity.getNickname(), entity.getDisability_type(), entity.getAid_type(), entity.getAddress_area(), entity.getGender(), entity.getBirth_day(), entity.getSign_type(), entity.isAct_flg(), entity.getReg_dt(), entity.getChg_dt());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserDto getUserByUserEmail(String email, String type) {
        Optional<UserEntity> userEntity = userRepository.findByEmailAndType(email, type);
        if (userEntity.isPresent()) {
            UserEntity entity = userEntity.get();
            return new UserDto(entity.getId(), entity.getEmail(), entity.getPassword(), entity.getRole(), entity.getUuid(), entity.getNickname(), entity.getDisability_type(), entity.getAid_type(), entity.getAddress_area(), entity.getGender(), entity.getBirth_day(), entity.getSign_type(), entity.isAct_flg(), entity.getReg_dt(), entity.getChg_dt());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = new User(userDto.getId(), userDto.getEmail(), userDto.getPassword(), userDto.getRole(), userDto.getUuid(), userDto.getNickname(), userDto.getDisability_type(), userDto.getAid_type(), userDto.getAddress_area(), userDto.getGender(), userDto.getBirth_day(), userDto.getSign_type(), userDto.isAct_flg(), userDto.getReg_dt(), userDto.getChg_dt());
        UserEntity userEntity = new UserEntity(user.getId(), user.getEmail(), passwordEncoder.encode(user.getPassword()), user.getRole(), user.getUuid(), user.getNickname(), user.getDisability_type(), user.getAid_type(), user.getAddress_area(), user.getGender(), user.getBirth_day(), user.getSign_type(), user.isAct_flg(), user.getReg_dt(), user.getChg_dt());
        UserEntity savedUser = userRepository.save(userEntity);
        return new UserDto(savedUser.getId(), savedUser.getEmail(), savedUser.getPassword(), savedUser.getRole(), savedUser.getUuid(), savedUser.getNickname(), savedUser.getDisability_type(), savedUser.getAid_type(), savedUser.getAddress_area(), savedUser.getGender(), savedUser.getBirth_day(), savedUser.getSign_type(), savedUser.isAct_flg(), savedUser.getReg_dt(), savedUser.getChg_dt());
    }

    @Override
    public UserDto updateUser(Long id, UserDto afterDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if(StringUtils.isNotBlank(afterDto.getPassword())) {
                userEntity.setPassword(passwordEncoder.encode(afterDto.getPassword()));
            }
            userEntity.setNickname(afterDto.getNickname());
            userEntity.setDisability_type(afterDto.getDisability_type());
            userEntity.setAid_type(afterDto.getAid_type());
            userEntity.setAddress_area(afterDto.getAddress_area());
            userEntity.setGender(afterDto.getGender());
            userEntity.setBirth_day(afterDto.getBirth_day());
            userEntity.setAct_flg(afterDto.isAct_flg());
            UserEntity updatedUser = userRepository.save(userEntity);
            return new UserDto(updatedUser.getId(), updatedUser.getEmail(), passwordEncoder.encode(updatedUser.getPassword()), updatedUser.getRole(), updatedUser.getUuid(), updatedUser.getNickname(), updatedUser.getDisability_type(), updatedUser.getAid_type(), updatedUser.getAddress_area(), updatedUser.getGender(), updatedUser.getBirth_day(), updatedUser.getSign_type(), updatedUser.isAct_flg(), updatedUser.getReg_dt(), updatedUser.getChg_dt());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public User getUserServiceByUserEmail(String email, String type) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAndType(email, type);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            return new User(userEntity.getId(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getRole(), userEntity.getUuid(), userEntity.getNickname(), userEntity.getDisability_type(), userEntity.getAid_type(), userEntity.getAddress_area(), userEntity.getGender(), userEntity.getBirth_day(), userEntity.getSign_type(), userEntity.isAct_flg(), userEntity.getReg_dt(), userEntity.getChg_dt());
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
