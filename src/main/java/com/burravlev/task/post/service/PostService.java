package com.burravlev.task.post.service;

import com.burravlev.task.post.domain.entity.PostEntity;
import com.burravlev.task.post.domain.model.PostCreationRequest;
import com.burravlev.task.post.domain.model.PostDeleteRequest;
import com.burravlev.task.post.domain.model.PostUpdateRequest;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<PostEntity> getPostsFromFollowedUsers(Long userId, int size, int page);

    PostEntity createNewPost(Long userId, PostCreationRequest request);

    Page<PostEntity> getAllUserPosts(Long userId, int size, int page);

    PostEntity update(Long userId, PostUpdateRequest request);

    PostEntity delete(Long userId, PostDeleteRequest request);
}
