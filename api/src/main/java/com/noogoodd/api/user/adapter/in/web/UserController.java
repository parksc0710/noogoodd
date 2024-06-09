package com.noogoodd.api.user.adapter.in.web;

import com.noogoodd.api.user.application.dto.UserDto;
import com.noogoodd.api.user.application.port.in.GetUserUseCase;
import com.noogoodd.api.user.application.port.in.SetUserUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final GetUserUseCase getUserUseCase;
    private final SetUserUserCase setUserUseCase;

    @PostMapping("/get")
    public UserDto getUser(@RequestBody final String uuid) {
        return getUserUseCase.getUserByUUID(uuid);
    }

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody UserDto userDto) {
        return setUserUseCase.registerUser(userDto);
    }

    @PostMapping("/update")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return setUserUseCase.updateUser(userDto.getId(), userDto);
    }
}
