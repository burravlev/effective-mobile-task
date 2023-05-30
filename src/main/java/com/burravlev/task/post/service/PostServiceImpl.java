package com.burravlev.task.post.service;

import com.burravlev.task.files.service.ImageStorageService;
import com.burravlev.task.friendship.service.FriendshipService;
import com.burravlev.task.post.domain.entity.PostModel;
import com.burravlev.task.post.domain.model.PostCreationRequest;
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
    public Page<PostModel> getPostsFromFollowedUsers(Long userId, int size, int page) {
        List<Long> followerUsersIds = friendshipService.getAllFollowedUsers(userId);
        return repository.findAllUsersPosts(followerUsersIds, PageRequest.of(page, size));
    }

    @Override
    public PostModel createNewPost(Long userId, PostCreationRequest request) {
        UserEntity user = userService.findById(userId);
        PostModel post = PostModel.builder()
                .creator(user)
                .created(LocalDateTime.now())
                .header(request.getHeader())
                .message(request.getMessage())
                .content(imageService.findAll(request.getContent()))
                .build();
        return repository.save(post);
    }

    @Override
    public Page<PostModel> getAllUserPosts(Long userId, int size, int page) {
        return repository.findAllUserPosts(userId, PageRequest.of(page, size));
    }
}
