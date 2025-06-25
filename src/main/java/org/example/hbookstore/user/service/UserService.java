package org.example.hbookstore.user.service;

import org.example.hbookstore.user.api.dto.CreateUserRequest;
import org.example.hbookstore.user.api.dto.UpdateUserRequest;
import org.example.hbookstore.user.api.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    public UserResponse createUser(CreateUserRequest request) throws Exception;
    public UserResponse getUser(Long id);
    public UserResponse updateUser(Long id, UpdateUserRequest request) throws Exception;
    public void deleteUser(Long id) throws Exception;
    public Page<UserResponse> getAllUsers(Pageable pageable);
    public UserResponse getUserByEmail(String email);
    public UserResponse getUserByUsername(String username);
}
