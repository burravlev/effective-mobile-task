package com.burravlev.task.friendship.service;

import com.burravlev.task.friendship.domain.entity.Friendship;
import com.burravlev.task.friendship.domain.model.FriendDeleteRequest;
import com.burravlev.task.friendship.domain.model.FriendshipRequest;
import com.burravlev.task.user.domain.entity.UserEntity;
import com.burravlev.task.friendship.exception.IllegalFriendshipException;
import com.burravlev.task.exception.NotFoundException;
import com.burravlev.task.friendship.repository.FriendshipRepository;
import com.burravlev.task.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public Friendship createOrApprove(Long userId, FriendshipRequest request) {
        Optional<Friendship> optional = repository.find(userId, request.getUserId());
        if (userId.equals(request.getUserId()))
            throw new IllegalFriendshipException("User can't send friendship request to himself");
        if (!optional.isPresent()) {
            return repository.save(Friendship.builder()
                    .requester(userService.findById(userId))
                    .addressee(userService.findById(request.getUserId()))
                    .status(Friendship.FriendshipStatus.REQUEST)
                    .build());
        }
        Friendship friendship = optional.get();
        if (friendship.getAddressee().getId().equals(userId) &&
                friendship.getStatus() == Friendship.FriendshipStatus.REQUEST) {
            friendship.setStatus(Friendship.FriendshipStatus.ACCEPTED);
            return repository.save(friendship);
        }
        return friendship;
    }

    @Override
    public Friendship find(Long user1, Long user2) {
        return repository.find(user1, user2)
                .orElseThrow(() -> new NotFoundException("Relationship not found"));
    }

    @Override
    @Transactional
    public Friendship accept(Long user, Long userToAccept) {
        Friendship friendship = find(user, userToAccept);
        if (friendship.getStatus() == Friendship.FriendshipStatus.ACCEPTED)
            throw new IllegalFriendshipException("Users are already friends");
        friendship.setStatus(Friendship.FriendshipStatus.ACCEPTED);
        return repository.save(friendship);
    }

    @Override
    public List<UserEntity> getAllUserRequests(Long userId, int size, int page) {
        return repository.findAsRequester(userId, Friendship.FriendshipStatus.REQUEST, Pageable.ofSize(size).withPage(page))
                .stream().map(Friendship::getAddressee).collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> getAllUserSubscribers(Long userId, int size, int page) {
        return repository.findAsAddressee(userId, Friendship.FriendshipStatus.REQUEST, Pageable.ofSize(size).withPage(page))
                .stream().map(Friendship::getRequester).collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> getAllFriends(Long id, int size, int page) {
        return repository.findAll(id, Friendship.FriendshipStatus.ACCEPTED, PageRequest.of(page, size))
                .stream().map(f -> {
                    if (!f.getAddressee().getId().equals(id))
                        return f.getAddressee();
                    else return f.getRequester();
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Friendship deleteFriend(Long userId, Long userToDeleteId) {
        Friendship friendship = find(userId, userToDeleteId);
        if (friendship.getStatus() == Friendship.FriendshipStatus.REQUEST)
            throw new IllegalFriendshipException("Users are not friends");
        UserEntity user = userService.findById(userId);
        UserEntity userToDelete = userService.findById(userToDeleteId);

        friendship.setAddressee(user);
        friendship.setRequester(userToDelete);
        friendship.setStatus(Friendship.FriendshipStatus.REQUEST);

        return repository.save(friendship);
    }

    @Override
    @Transactional
    public Friendship unsubscribe(Long userId, Long unsubscribeFromId) {
        Friendship friendship = find(userId, unsubscribeFromId);
        if (friendship.getStatus() != Friendship.FriendshipStatus.REQUEST)
            throw new IllegalFriendshipException("User is not subscribed to user with id: " + unsubscribeFromId);
        repository.delete(friendship);
        return friendship;
    }

    @Override
    public List<Long> getAllFollowedUsers(Long id) {
        return repository.findAllFollowedUsers(id)
                .stream().map(f -> {
                    if (f.getAddressee().getId().equals(id))
                        return f.getRequester().getId();
                    else return f.getAddressee().getId();
                }).collect(Collectors.toList());
    }

    @Override
    public Friendship delete(Long id, FriendDeleteRequest request) {
        Friendship friendship = find(id, request.getUserId());
        if (friendship.getStatus() == Friendship.FriendshipStatus.REQUEST)
            if (friendship.getRequester().getId().equals(id)) {
                repository.delete(friendship);
                friendship.setId(null);
                return friendship;
            } else {
                throw new IllegalFriendshipException("Users are not friends");
            }

        friendship.setAddressee(userService.findById(id));
        friendship.setRequester(userService.findById(request.getUserId()));
        friendship.setStatus(Friendship.FriendshipStatus.REQUEST);

        return repository.save(friendship);
    }
}
