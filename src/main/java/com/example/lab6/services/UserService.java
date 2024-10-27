package com.example.lab6.services;

import com.example.lab6.models.User;
import com.example.lab6.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User with id={%s} is not found.".formatted(id)));
    }

    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Such email already in use: %s".formatted(user.getEmail()));
        }
        return userRepository.save(user);
    }

    public User update(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User with id={%s} is not found.".formatted(user.getId()));
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Such email already in use: %s".formatted(user.getEmail()));
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with id={%s} is not found.".formatted(id));
        }
        userRepository.deleteById(id);
    }
}
