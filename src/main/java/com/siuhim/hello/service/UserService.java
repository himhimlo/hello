package com.siuhim.hello.service;

import com.siuhim.hello.dto.UserDto;
import com.siuhim.hello.exception.ValidationException;

public interface UserService {

    String retrieveName(String username);

    void register(UserDto userDto) throws ValidationException;
}
