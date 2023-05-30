package com.burravlev.task.friendship.service;

import com.burravlev.task.friendship.domain.model.FriendshipRequest;
import com.burravlev.task.friendship.domain.entity.Friendship;
import com.burravlev.task.user.domain.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendshipService {
    @Transactional
    Friendship createOrApprove(Long requester, FriendshipRequest request);

    Friendship find(Long user1, Long user2);

    Friendship accept(Long user, Long userToAccept);

    List<UserEntity> getAllUserRequests(Long userId, int size, int page);

    List<UserEntity> getAllUserSubscribers(Long userId, int size, int page);

    List<UserEntity> getAllFriends(Long id, int size, int page);

    Friendship deleteFriend(Long userId, Long userToDeleteId);

    Friendship unsubscribe(Long userId, Long unsubscribeFromId);

    List<Long> getAllFollowedUsers(Long id);
}
