package com.samsung.rest.controller;

import com.samsung.exception.UserAlreadyExistsException;
import com.samsung.exception.UserNotFoundException;
import com.samsung.repository.UserRepository;
import com.samsung.rest.dto.UserDto;
import com.samsung.service.UserService;
import com.samsung.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
    @Autowired
    private final UserServiceImpl userService;

    @GetMapping("/user/{email}")
    public UserDto findUserByEmail(@PathVariable("email") String email){
        return UserDto.toDto(userService.findByEmail(email));
    }
    @PostMapping("/user")
    public UserDto insertUser(@RequestBody UserDto userDto){
        return UserDto.toDto(userService.save(UserDto.fromDto(userDto)));
    }
    @ExceptionHandler({UserAlreadyExistsException.class, UserNotFoundException.class})
    public ResponseEntity<String> handlerUserException(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
