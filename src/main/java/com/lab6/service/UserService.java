package com.lab6.service;

import com.lab6.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<String, User> usersByUsername = new ConcurrentHashMap<>();
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public User createUser(User user) {
        validateUserData(user);

        if (usersByUsername.containsKey(user.getUsername().toLowerCase())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        if (usersByEmail.containsKey(user.getEmail().toLowerCase())) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }

        User newUser = new User();
        newUser.setId(idGenerator.getAndIncrement());
        newUser.setUsername(user.getUsername().trim());
        newUser.setEmail(user.getEmail().trim().toLowerCase());
        newUser.setFirstName(user.getFirstName() != null ? user.getFirstName().trim() : "");
        newUser.setLastName(user.getLastName() != null ? user.getLastName().trim() : "");
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        users.put(newUser.getId(), newUser);
        usersByUsername.put(newUser.getUsername().toLowerCase(), newUser);
        usersByEmail.put(newUser.getEmail().toLowerCase(), newUser);

        return newUser;
    }

    public User updateUser(Long id, User user) {
        User existingUser = users.get(id);
        if (existingUser == null) {
            throw new NoSuchElementException("User with id " + id + " not found");
        }

        if (user.getUsername() != null && !user.getUsername().trim().isEmpty()) {
            String newUsername = user.getUsername().trim();
            if (!newUsername.equalsIgnoreCase(existingUser.getUsername())) {
                if (usersByUsername.containsKey(newUsername.toLowerCase())) {
                    throw new IllegalArgumentException("Username already exists: " + newUsername);
                }
                usersByUsername.remove(existingUser.getUsername().toLowerCase());
                existingUser.setUsername(newUsername);
                usersByUsername.put(newUsername.toLowerCase(), existingUser);
            }
        }

        if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            String newEmail = user.getEmail().trim().toLowerCase();
            if (!newEmail.equals(existingUser.getEmail())) {
                if (!EMAIL_PATTERN.matcher(newEmail).matches()) {
                    throw new IllegalArgumentException("Invalid email format: " + newEmail);
                }
                if (usersByEmail.containsKey(newEmail)) {
                    throw new IllegalArgumentException("Email already exists: " + newEmail);
                }
                usersByEmail.remove(existingUser.getEmail());
                existingUser.setEmail(newEmail);
                usersByEmail.put(newEmail, existingUser);
            }
        }

        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName().trim());
        }
        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName().trim());
        }
        existingUser.setUpdatedAt(LocalDateTime.now());

        return existingUser;
    }

    public void deleteUser(Long id) {
        User removed = users.remove(id);
        if (removed == null) {
            throw new NoSuchElementException("User with id " + id + " not found");
        }
        usersByUsername.remove(removed.getUsername().toLowerCase());
        usersByEmail.remove(removed.getEmail().toLowerCase());
    }

    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username.toLowerCase()));
    }

    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(usersByEmail.get(email.toLowerCase()));
    }

    private void validateUserData(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(user.getEmail().trim()).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + user.getEmail());
        }
    }
}

