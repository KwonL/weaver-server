package com.miracle.weaver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpDTO.Request.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpDTO.Request request = (SignUpDTO.Request) target;
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            errors.rejectValue("username", "이미 존재하는 아이디입니다.");
        }
        if (userRepository.findByNickname(request.getNickname()).isPresent()) {
            errors.rejectValue("nickname", "이미 존재하는 닉네임입니다.");
        }
    }
}
