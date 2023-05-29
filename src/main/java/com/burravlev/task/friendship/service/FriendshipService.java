package com.burravlev.task.friendship.service;

import com.burravlev.task.friendship.domain.model.FriendshipRequest;
import com.burravlev.task.friendship.domain.entity.Friendship;
import com.burravlev.task.user.domain.model.UserModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendshipService {
    @Transactional
    Friendship createOrApprove(Long requester, FriendshipRequest request);

    Friendship find(Long user1, Long user2);

    Friendship accept(Long user, Long userToAccept);

    List<UserModel> getAllUserRequests(Long userId, int size, int page);

    List<UserModel> getAllUserSubscribers(Long userId, int size, int page);

    List<UserModel> getAllFriends(Long id, int size, int page);

    Friendship deleteFriend(Long userId, Long userToDeleteId);

    Friendship unsubscribe(Long userId, Long unsubscribeFromId);
}
