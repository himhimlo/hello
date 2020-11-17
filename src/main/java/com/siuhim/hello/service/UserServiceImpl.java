package com.siuhim.hello.service;

import com.siuhim.hello.dao.UserRepository;
import com.siuhim.hello.dto.UserDto;
import com.siuhim.hello.exception.ValidationException;
import com.siuhim.hello.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public String retrieveName(String username) {
        return userRepository.findByUsername(username).map(User::getName).orElse(null);
    }

    @Override
    public void register(UserDto userDto) throws ValidationException {
        List<String> msgList = new ArrayList<>();
        if (userDto.getName().isBlank()) {
            msgList.add("Missing name");
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            msgList.add("Wrong re-type password");
        }
        if (userExists(userDto.getUsername())) {
            msgList.add("Username exists");
        }
        if (!msgList.isEmpty()) {
            throw new ValidationException(msgList);
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setActive(true);
        user.setRoles("USER");
        userRepository.save(user);
    }

    private boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
