package com.solmaz.security.service.business;

import com.solmaz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        var user = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username + " not found "));

        var userDetails = User.builder().username(user.getUserId()).password("").roles("User").authorities(new ArrayList<>()).build();

        return userDetails;
    }
}
