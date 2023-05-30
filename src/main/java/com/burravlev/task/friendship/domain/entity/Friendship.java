package com.burravlev.task.friendship.domain.entity;

import com.burravlev.task.user.domain.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    private UserEntity requester;
    @ManyToOne
    @JoinColumn(name = "addressee_id", referencedColumnName = "id")
    private UserEntity addressee;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status = FriendshipStatus.REQUEST;

    public enum FriendshipStatus {
        ACCEPTED, REQUEST
    }
}
