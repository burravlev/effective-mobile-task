package com.burravlev.task.post.service;

import com.burravlev.task.post.domain.model.PostModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    Page<PostModel> getPostsFromUsers(List<Long> users, int size, int page);
}
