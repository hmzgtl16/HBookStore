package org.example.hbookstore.user.service;


import org.example.hbookstore.user.domain.UserRepository;
import org.example.hbookstore.user.domain.enums.UserStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map((user) ->
                        new User(
                                user.getUsername(),
                                user.getPassword(),
                                user.getStatus().equals(UserStatus.ACTIVE),
                                true, // the account is not expired
                                true, // credentials are not expired
                                true, // the account is not locked
                                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                        )
                )
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
