package com.kasperovich.desoccer.security;

import com.kasperovich.desoccer.models.User;
import com.kasperovich.desoccer.repository.UserRepository;
import com.kasperovich.desoccer.util.ValidCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ValidCheck validCheck;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(validCheck.isEmailValid(username)){
            try {
                /*Find user in DB*/
                Optional<User> searchResult = userRepository.findUserByEmail(username);

                if (searchResult.isPresent()) {
                    User user = searchResult.get();

                    /*We are creating Spring Security User object*/
                    return new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getCredentials().getPassword(),
                            AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().getName().toString()+",")
                    );
                } else {
                    throw new UsernameNotFoundException(String.format("No user found with email '%s'.", username));
                }
            } catch (Exception e) {
                throw new UsernameNotFoundException("User with this email not found");
            }
        }
        try {
            /*Find user in DB*/
            Optional<User> searchResult = userRepository.findUserByCredentialsLogin(username);

            if (searchResult.isPresent()) {
                User user = searchResult.get();

                /*We are creating Spring Security User object*/
                return new org.springframework.security.core.userdetails.User(
                        user.getCredentials().getLogin(),
                        user.getCredentials().getPassword(),
                        AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().getName().toString()+",")
                );
            } else {
                throw new UsernameNotFoundException(String.format("No user found with login '%s'.", username));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User with this login not found");
        }
    }



}
