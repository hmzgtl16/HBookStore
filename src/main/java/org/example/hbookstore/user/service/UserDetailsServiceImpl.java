package org.example.hbookstore.user.service;


import org.example.hbookstore.user.domain.UserRepository;
import org.example.hbookstore.user.domain.enums.UserStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user ->
                        User.builder()
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .authorities(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                                .disabled(!user.getStatus().equals(UserStatus.ACTIVE))
                                .accountExpired(false)
                                .accountLocked(false)
                                .credentialsExpired(false)
                                .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
