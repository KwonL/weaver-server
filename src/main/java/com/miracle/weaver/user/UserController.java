package com.miracle.weaver.user;


import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignUpValidator signUpValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }

    @GetMapping("")
    public List<User> userList() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("signup")
    public SignUpDTO.Response user(@Valid @RequestBody SignUpDTO.Request request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .nickname(request.getNickname())
            .personality(request.getPersonality())
            .isAdmin(false)
            .build();
        userRepository.save(user);
        return SignUpDTO.Response.builder()
            .username(user.getUsername())
            .personality(user.getPersonality())
            .build();
    }
}