package com.miracle.weaver.community;

import com.miracle.weaver.community.dto.BoardDTO;
import com.miracle.weaver.community.dto.CommentDTO;
import com.miracle.weaver.community.dto.CommentDTO.Create;
import com.miracle.weaver.community.entity.BoardEntity;
import com.miracle.weaver.community.entity.CommentEntity;
import com.miracle.weaver.community.repository.BoardRepository;
import com.miracle.weaver.community.repository.CommentRepository;
import com.miracle.weaver.user.User;
import com.miracle.weaver.user.UserAdapter;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final BoardRepository boardRepository;
    private final ModelMapper mapper = new ModelMapper();
    private final EntityManager entityManager;
    private final CommentRepository commentRepository;

    public Page<BoardDTO.List> getBoardList(Pageable pageable) {
        Page<BoardEntity> entities = boardRepository.findAll(pageable);

        return entities.map((e) -> mapper.map(e, BoardDTO.List.class));
    }

    public BoardDTO.Detail getBoardDetail(int id) {
        return mapper
            .map(boardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "not found")), BoardDTO.Detail.class);
    }

    public BoardDTO.Detail createPost(BoardDTO.Create request, User user) {
        BoardEntity entity = mapper.map(request, BoardEntity.class);
        entity.setUser(user);
        entity = boardRepository.save(entity);
        return mapper.map(entity, BoardDTO.Detail.class);
    }

    public CommentDTO.Detail createComment(Create request, int boardId, User user) {
        if (!boardRepository.existsById(boardId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "board not found");
        }
        CommentEntity commentEntity = CommentEntity.builder()
            .content(request.getContent())
            .board(BoardEntity.builder().id(boardId).build())
            .user(user)
            .build();
        commentEntity = commentRepository.save(commentEntity);
        return mapper.map(commentEntity, CommentDTO.Detail.class);
    }
}
