package com.burravlev.task.user.repository;

import com.burravlev.task.user.domain.model.Friendship;
import com.burravlev.task.user.domain.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static com.burravlev.task.user.domain.model.Friendship.FriendshipStatus;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    @Query("FROM Friendship f WHERE f.status = :status AND (f.requester.id = :userId OR f.addressee.id = :userId)")
    Page<Friendship> findAll(
            @Param("userId") Long userId,
            @Param("status") FriendshipStatus status, Pageable pageable);

    @Query("FROM Friendship f WHERE (f.requester.id = :user1 AND f.addressee.id = :user2) OR (f.requester.id = :user2 AND f.addressee.id = :user1)")
    Optional<Friendship> find(@Param("user1") Long user1, @Param("user2") Long user2);

    @Query("FROM Friendship f WHERE f.addressee.id = :userId AND f.status = :status")
    Page<Friendship> findAsAddressee(@Param("userId") Long userId, @Param("status") FriendshipStatus status, Pageable pageable);

    @Query("FROM Friendship f WHERE f.requester.id = :userId AND f.status = :status")
    Page<Friendship> findAsRequester(@Param("userId") Long userId, @Param("status") FriendshipStatus status, Pageable pageable);
}
