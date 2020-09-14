package com.miracle.weaver.community.repository;

import com.miracle.weaver.community.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {
}
