package com.burravlev.task.post.service;

import com.burravlev.task.post.domain.entity.PostModel;
import com.burravlev.task.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    @Override
    @Transactional
    public Page<PostModel> getPostsFromUsers(List<Long> users, int size, int page) {
        return repository.findAll(users, PageRequest.of(page, size));
    }
}
