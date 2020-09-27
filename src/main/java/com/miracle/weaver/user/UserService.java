package com.miracle.weaver.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsername(s);

        if (optUser.isEmpty()) {
            return null;
        } else {
            User user = optUser.get();
            return new UserAdapter(user.getId(), user.getUsername(), user.getPassword(), user.isAdmin());
        }
    }
}
