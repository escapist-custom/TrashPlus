package com.samsung.security;

import com.samsung.domain.User;
import com.samsung.exception.UserNotFoundException;
import com.samsung.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository = null;
    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException(
                "user with email " + username + " was not found"
        ));
        return new SecurityUser(user);
    }
}
