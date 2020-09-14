package com.miracle.weaver.community.repository;

import com.miracle.weaver.community.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<BoardEntity, Integer> {

    Page<BoardEntity> findAll(Pageable pageable);
}

