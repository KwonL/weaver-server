package com.miracle.weaver.community;


import com.miracle.weaver.community.dto.BoardDTO;
import com.miracle.weaver.community.dto.CommentDTO;
import com.miracle.weaver.community.entity.CategoryEntity;
import com.miracle.weaver.community.validator.CategoryValidator;
import com.miracle.weaver.user.User;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final CategoryValidator categoryValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        if (webDataBinder.getTarget() != null) {
            if (webDataBinder.getTarget().getClass().equals(BoardDTO.Create.class)) {
                webDataBinder.addValidators(categoryValidator);
            }
        }
    }

    @GetMapping("board")
    public Page<BoardDTO.List> boardList(Pageable pageable,
        @RequestParam(value = "category_id", required = false) Integer category_id) {
        return communityService.getBoardList(pageable, category_id);
    }

    @GetMapping("board/{id}")
    public BoardDTO.Detail boardDetail(@PathVariable int id) {
        return communityService.getBoardDetail(id);
    }

    @PostMapping("board")
    public BoardDTO.Detail createPost(@Valid @RequestBody BoardDTO.Create request,
        @AuthenticationPrincipal User user) {
        return communityService.createPost(request, user);
    }

    @PostMapping("board/{boardId}/comment")
    public CommentDTO.Detail createComment(
        @Valid @RequestBody CommentDTO.Create request,
        @PathVariable int boardId, @AuthenticationPrincipal User user) {
        return communityService.createComment(request, boardId, user);
    }

    @GetMapping("category")
    public List<CategoryEntity> categoryList() {
        return communityService.categoryList();
    }
}
