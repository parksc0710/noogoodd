package com.noogoodd.api.user.application.port.in;

import com.noogoodd.api.user.application.dto.UserDto;
import com.noogoodd.api.user.domain.User;

public interface GetUserUseCase {
    UserDto getUserById(Long id);
    UserDto getUserByUserEmail(String email, String type);
    User getUserServiceByUserEmail(String email, String type);
}
