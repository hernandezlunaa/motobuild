package com.motobuild.service;

import com.motobuild.model.User;
import com.motobuild.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

@Service
public class AuthService {

    private final JdbcTemplate jdbcTemplate;

    public static final String LOGGED_IN_USER_ID = "loggedInUserId";

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public User register(String firstName,
                         String lastName,
                         String email,
                         String password,
                         String confirmPassword) {
        String cleanEmail = email.trim().toLowerCase();

        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match.");
        }

        if (password.length() < 4) {
            throw new RuntimeException("Password must be at least 4 characters.");
        }

        if (userRepository.findByEmail(cleanEmail).isPresent()) {
            throw new RuntimeException("An account with that email already exists.");
        }

        User user = new User();
        user.setFirstName(firstName.trim());
        user.setLastName(lastName.trim());
        user.setEmail(cleanEmail);
        user.setPasswordHash(hashPassword(password));

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        String cleanEmail = email.trim().toLowerCase();

        Optional<User> userOptional = userRepository.findByEmail(cleanEmail);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid email or password.");
        }

        User user = userOptional.get();

        if (!user.getPasswordHash().equals(hashPassword(password))) {
            throw new RuntimeException("Invalid email or password.");
        }

        return user;
    }

    public void loginSession(HttpSession session, User user) {
        session.setAttribute(LOGGED_IN_USER_ID, user.getUserId());
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public Integer getLoggedInUserId(HttpSession session) {
        Object userId = session.getAttribute(LOGGED_IN_USER_ID);

        if (userId instanceof Integer) {
            return (Integer) userId;
        }

        return null;
    }

    public boolean isLoggedIn(HttpSession session) {
        return getLoggedInUserId(session) != null;
    }

    public User getLoggedInUser(HttpSession session) {
        Integer userId = getLoggedInUserId(session);

        if (userId == null) {
            return null;
        }

        return userRepository.findById(userId).orElse(null);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Could not hash password.", e);
        }
    }

    public void verifyPassword(User user, String password) {
        if (user == null) {
            throw new RuntimeException("You must be logged in.");
        }

        if (!user.getPasswordHash().equals(hashPassword(password))) {
            throw new RuntimeException("Password is incorrect.");
        }
    }

    public void deleteAccount(HttpSession session, String password) {
        User user = getLoggedInUser(session);

        if (user == null) {
            throw new RuntimeException("You must be logged in.");
        }

        verifyPassword(user, password);

        userRepository.delete(user);
        session.invalidate();
    }

    public void updateProfile(HttpSession session,
                              String firstName,
                              String lastName,
                              String email,
                              String currentPassword) {
        User user = getLoggedInUser(session);

        if (user == null) {
            throw new RuntimeException("You must be logged in.");
        }

        verifyPassword(user, currentPassword);

        String cleanEmail = email.trim().toLowerCase();

        Optional<User> existingUser = userRepository.findByEmail(cleanEmail);

        if (existingUser.isPresent() && !existingUser.get().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("An account with that email already exists.");
        }

        user.setFirstName(firstName.trim());
        user.setLastName(lastName.trim());
        user.setEmail(cleanEmail);

        userRepository.save(user);

        logAccountActivity(
                user.getUserId(),
                "PROFILE_UPDATED",
                "Updated account profile"
        );
    }

    public void changePassword(HttpSession session,
                               String currentPassword,
                               String newPassword,
                               String confirmNewPassword) {
        User user = getLoggedInUser(session);

        if (user == null) {
            throw new RuntimeException("You must be logged in.");
        }

        verifyPassword(user, currentPassword);

        if (!newPassword.equals(confirmNewPassword)) {
            throw new RuntimeException("New passwords do not match.");
        }

        if (newPassword.length() < 4) {
            throw new RuntimeException("New password must be at least 4 characters.");
        }

        user.setPasswordHash(hashPassword(newPassword));
        userRepository.save(user);

        logAccountActivity(
                user.getUserId(),
                "PASSWORD_CHANGED",
                "Changed account password"
        );
    }

    private void logAccountActivity(Integer userId, String activityType, String activityMessage) {
        jdbcTemplate.update(
                """
                INSERT INTO account_activity (user_id, activity_type, activity_message)
                VALUES (?, ?, ?)
                """,
                userId,
                activityType,
                activityMessage
        );
    }
}