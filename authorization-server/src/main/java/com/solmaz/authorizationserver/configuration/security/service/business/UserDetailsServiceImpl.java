package com.solmaz.authorizationserver.configuration.security.service.business;

import com.solmaz.authorizationserver.configuration.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        var authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("xauthority"));
        var userDetails = User.builder().username(user.getUserId()).password("").roles("User").authorities(authorities).build();

        return userDetails;
    }
}
