package com.miracle.weaver.community;


import com.miracle.weaver.community.dto.BoardDTO;
import com.miracle.weaver.community.validator.CategoryValidator;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final CategoryValidator categoryValidator;

    @GetMapping("board")
    public Page<BoardDTO.List> boardList(Pageable pageable) {
        return communityService.getBoardList(pageable);
    }

    @GetMapping("board/{id}")
    public BoardDTO.Detail boardDetail(@PathVariable int id) {
        return communityService.getBoardDetail(id);
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(categoryValidator);
    }

    @PostMapping("board")
    public BoardDTO.Detail createPost(@Valid @RequestBody BoardDTO.Create boardPost) {
        return communityService.createPost(boardPost);
    }
}
