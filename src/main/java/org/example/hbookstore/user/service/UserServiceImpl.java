package org.example.hbookstore.user.service;

import org.example.hbookstore.user.api.dto.CreateUserRequest;
import org.example.hbookstore.user.api.dto.UpdateUserRequest;
import org.example.hbookstore.user.api.dto.UserResponse;
import org.example.hbookstore.user.domain.User;
import org.example.hbookstore.user.domain.UserRepository;
import org.example.hbookstore.user.mapping.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserResponse createUser(CreateUserRequest request) throws Exception {
        validateNewUser(request);

        User user = userMapper.toEntity(request);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUser(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow();
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow();
        if (request.email() != null && !request.email().equals(user.getEmail())) {
            throw new Exception("Email already exists");
        }

        User updatedUser = userMapper.updateEntity(user, request);
        return userMapper.toResponse(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        if (!userRepository.existsById(id)) {
            throw new Exception("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow();
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse)
                .orElseThrow();
    }

    private void validateNewUser(CreateUserRequest request) throws Exception {
        if (!userRepository.existsByEmail(request.email())) {
            throw new Exception("Email already exists");
        }
        if (!userRepository.existsByUsername(request.username())) {
            throw new Exception("Username already exists");
        }
    }
}
