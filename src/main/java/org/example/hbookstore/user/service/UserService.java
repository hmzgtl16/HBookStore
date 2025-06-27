package org.example.hbookstore.user.service;

import org.example.hbookstore.user.api.dto.CreateUserRequest;
import org.example.hbookstore.user.api.dto.UpdateUserRequest;
import org.example.hbookstore.user.api.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    UserResponse createUser(CreateUserRequest request) throws Exception;

    UserResponse getUser(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest request) throws Exception;

    void deleteUser(Long id) throws Exception;

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserByEmail(String email);

    UserResponse getUserByUsername(String username);
}
