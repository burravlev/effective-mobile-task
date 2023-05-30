package com.burravlev.task.post.service;

import com.burravlev.task.auth.exception.UnauthorizedException;
import com.burravlev.task.exception.NotFoundException;
import com.burravlev.task.files.service.ImageStorageService;
import com.burravlev.task.friendship.service.FriendshipService;
import com.burravlev.task.post.domain.entity.PostEntity;
import com.burravlev.task.post.domain.model.PostCreationRequest;
import com.burravlev.task.post.domain.model.PostDeleteRequest;
import com.burravlev.task.post.domain.model.PostUpdateRequest;
import com.burravlev.task.post.repository.PostRepository;
import com.burravlev.task.user.domain.entity.UserEntity;
import com.burravlev.task.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final FriendshipService friendshipService;
    private final UserService userService;
    private final ImageStorageService imageService;


    @Override
    public Page<PostEntity> getPostsFromFollowedUsers(Long userId, int size, int page) {
        List<Long> followerUsersIds = friendshipService.getAllFollowedUsers(userId);
        return repository.findAllUsersPosts(followerUsersIds, PageRequest.of(page, size));
    }

    @Override
    public PostEntity createNewPost(Long userId, PostCreationRequest request) {
        UserEntity user = userService.findById(userId);
        PostEntity post = PostEntity.builder()
                .creator(user)
                .created(LocalDateTime.now())
                .header(request.getHeader())
                .message(request.getMessage())
                .content(imageService.findAll(request.getContent()))
                .build();
        return repository.save(post);
    }

    @Override
    public Page<PostEntity> getAllUserPosts(Long userId, int size, int page) {
        return repository.findAllUserPosts(userId, PageRequest.of(page, size));
    }

    public PostEntity find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post doesn't exists ID: " + id));
    }

    @Override
    public PostEntity update(Long userId, PostUpdateRequest request) {
        PostEntity post = find(request.getId());
        if (!post.getCreator().getId().equals(userId))
            throw new UnauthorizedException("No permission to delete this post");

        post.setContent(imageService.findAll(request.getContent()));
        post.setHeader(request.getHeader());
        post.setMessage(request.getMessage());
        return repository.save(post);
    }

    @Override
    public PostEntity delete(Long userId, PostDeleteRequest request) {
        PostEntity post = find(request.getPostId());
        if (!post.getCreator().getId().equals(userId))
            throw new UnauthorizedException("No permission to delete this post");
        repository.delete(post);
        return post;
    }
}
