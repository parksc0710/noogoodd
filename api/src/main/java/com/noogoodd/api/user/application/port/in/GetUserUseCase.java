package com.noogoodd.api.user.application.port.in;

import com.noogoodd.api.user.application.dto.UserDto;

public interface GetUserUseCase {
    UserDto getUserByUUID(String uuid);
}
