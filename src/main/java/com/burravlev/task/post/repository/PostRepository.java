package com.burravlev.task.post.repository;

import com.burravlev.task.post.domain.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("FROM PostEntity p WHERE p.creator.id IN :users ORDER BY p.created DESC")
    Page<PostEntity> findAllUsersPosts(@Param("users") List<Long> users, Pageable pageable);

    @Query("FROM PostEntity p WHERE p.creator.id = :userId")
    Page<PostEntity> findAllUserPosts(@Param("userId") Long userId, Pageable pageable);
}
