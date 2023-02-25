package ru.lazarenko.springrest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lazarenko.springrest.entity.User;
import ru.lazarenko.springrest.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/first/users", produces = "application/xml")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Integer userId, @RequestBody User user) {
        userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Integer userId) {
        userService.deleteUserById(userId);
    }


}
