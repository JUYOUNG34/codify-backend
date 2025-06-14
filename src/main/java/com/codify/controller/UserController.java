package com.codify.controller;


import com.codify.entity.User;
import com.codify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findByIsActiveTrue();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<User> getUserByNickname(@PathVariable String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);

        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(@RequestParam String keyword) {
        List<User> user = userRepository.searchByKeyword(keyword);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/tech-stack/{techStack}")
    public ResponseEntity<List<User>> getUserByTechStack(@PathVariable String techStack) {
        List<User> users = userRepository.findByTechStack(techStack);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        if (userRepository.existsByNickname(user.getNickname())) {
            return ResponseEntity.badRequest().build();
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> OptionalUser = userRepository.findById(id);

        if(OptionalUser.isPresent()) {
            User user = OptionalUser.get();
            user.setNickname(userDetails.getNickname());
            user.setEmail(userDetails.getEmail());
            user.setBio(userDetails.getBio());
            user.setTechStacks(userDetails.getTechStacks());
            user.setCareerLevel(userDetails.getCareerLevel());
            user.setGithubUsername(userDetails.getGithubUsername());
            user.setPortfolioUrl(userDetails.getPortfolioUrl());

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User userToDelete = user.get();
            userToDelete.setIsActive(false);
            userRepository.save(userToDelete);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userRepository.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity<Boolean> checkNicknameExists(@PathVariable String nickname) {
        boolean exists = userRepository.existsByNickname(nickname);
        return ResponseEntity.ok(exists);
    }
}

