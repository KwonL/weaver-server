package com.miracle.weaver.community.repository;

import com.miracle.weaver.community.entity.LikeEntity;
import com.miracle.weaver.community.entity.LikeEntityId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LikeRepository extends CrudRepository<LikeEntity, LikeEntityId> {

    LikeEntity findByBoardId(Integer boardId);

    @Modifying
    @Transactional
    @Query("DELETE FROM LikeEntity le WHERE le.boardId = :boardId AND le.userId = :userId")
    int deleteByBoardIdAndUserId(@Param("boardId") int boardId, @Param("userId") int userId);
}
