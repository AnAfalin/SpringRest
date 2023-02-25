package ru.lazarenko.springrest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springrest.entity.User;
import ru.lazarenko.springrest.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void createUser(User user){
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User by id='%d' not found".formatted(id)));
    }

    @Transactional
    public void updateUser(Integer id, User user){
        User updatableUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User by id='%d' not found".formatted(id)));

        updatableUser.setName(user.getName());
        updatableUser.setLogin(user.getLogin());
        updatableUser.setPassword(user.getPassword());

        userRepository.save(updatableUser);
    }

    @Transactional
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

}
