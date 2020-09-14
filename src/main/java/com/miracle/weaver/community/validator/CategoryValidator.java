package com.miracle.weaver.community.validator;


import com.miracle.weaver.community.dto.BoardDTO;
import com.miracle.weaver.community.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class CategoryValidator implements Validator {

    private final CategoryRepository categoryRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(BoardDTO.Create.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardDTO.Create create = (BoardDTO.Create) target;
        if (!categoryRepository.existsById(create.getCategory().getId())) {
            errors.rejectValue("category", "존재하지 않는 카테고리입니다.");
        }
    }
}
