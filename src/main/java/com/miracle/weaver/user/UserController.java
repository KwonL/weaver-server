package com.miracle.weaver.user;


import com.miracle.weaver.community.dto.BoardDTO;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignUpValidator signUpValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        if (webDataBinder.getTarget() != null) {
            if (webDataBinder.getTarget().getClass().equals(SignUpDTO.Request.class)) {
                webDataBinder.addValidators(signUpValidator);
            }
        }
    }

    @PatchMapping("")
    public SignUpDTO.Response updateUser(
        @Valid @RequestBody SignUpDTO.PatchRequest request, @AuthenticationPrincipal User user) {
        Optional<User> targetUser = userRepository.findByUsername(request.getUsername());
        if (targetUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
        User userObj = targetUser.get();
        System.out.println(userObj.getNickname());
        if (userObj.getUsername().equals(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not authorized");
        }
        if (request.getNickname() != null && !request.getNickname().isEmpty()) {
            userObj.setNickname(request.getNickname());
        }
        if (request.getPersonality() != null && !request.getPersonality().isEmpty()) {
            userObj.setPersonality(request.getPersonality());
        }
        userRepository.save(userObj);

        return SignUpDTO.Response.builder()
            .username(userObj.getUsername())
            .personality(userObj.getPersonality())
            .build();
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