package com.burravlev.task.post.repository;

import com.burravlev.task.post.domain.model.PostModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostModel, Long> {
    @Query("FROM PostModel p WHERE p.creator.id IN :users ORDER BY p.created")
    Page<PostModel> findAll(@Param("users") List<Long> users, Pageable pageable);
}
