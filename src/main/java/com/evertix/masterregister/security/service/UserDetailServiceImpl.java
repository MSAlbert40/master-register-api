package com.evertix.masterregister.security.service;

import com.evertix.masterregister.model.User;
import com.evertix.masterregister.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String parameter) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(parameter, parameter)
                .orElseThrow(() -> new UsernameNotFoundException("Parameter: "+ parameter + "not found."));
        return UserDetailsImpl.build(user);
    }
}
