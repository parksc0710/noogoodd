package com.noogoodd.api.user.application.port.in;

import com.noogoodd.api.user.application.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface GetUserUseCase {
    UserDto getUserById(Long id);
    UserDto getUserByUUID(String uuid);
    UserDto getUserByUsername(String username);
    UserDetails getUserDetailsByUsername(String username);
}
