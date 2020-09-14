package com.miracle.weaver.community;

import com.miracle.weaver.community.dto.BoardDTO;
import com.miracle.weaver.community.entity.BoardEntity;
import com.miracle.weaver.community.entity.CategoryEntity;
import com.miracle.weaver.community.repository.BoardRepository;
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

    public Page<BoardDTO.List> getBoardList(Pageable pageable) {
        Page<BoardEntity> entities = boardRepository.findAll(pageable);

        return entities.map((e) -> mapper.map(e, BoardDTO.List.class));
    }

    public BoardDTO.Detail getBoardDetail(int id) {
        return mapper
            .map(boardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "not found")), BoardDTO.Detail.class);
    }

    public BoardDTO.Detail createPost(BoardDTO.Create detail) {
        BoardEntity entity = mapper.map(detail, BoardEntity.class);

        entity = boardRepository.save(entity);

        return mapper.map(entity, BoardDTO.Detail.class);
    }
}
