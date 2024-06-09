package com.noogoodd.api.user.application.port.in;

import com.noogoodd.api.user.application.dto.UserDto;

public interface SetUserUserCase {
    UserDto registerUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto afterDto);
}
