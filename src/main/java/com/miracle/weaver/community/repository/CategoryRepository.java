package com.miracle.weaver.community.repository;

import com.miracle.weaver.community.entity.CategoryEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAll();
}
