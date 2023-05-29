package com.burravlev.task.post.service;

import com.burravlev.task.post.domain.entity.PostModel;
import com.burravlev.task.post.domain.model.PostCreationRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    Page<PostModel> getPostsFromFollowedUsers(Long userId, int size, int page);

    PostModel createNewPost(Long userId, PostCreationRequest request);
}
