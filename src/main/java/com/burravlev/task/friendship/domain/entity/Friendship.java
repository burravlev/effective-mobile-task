package com.burravlev.task.friendship.domain.entity;

import com.burravlev.task.user.domain.model.UserModel;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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
    private UserModel requester;
    @ManyToOne
    @JoinColumn(name = "addressee_id", referencedColumnName = "id")
    private UserModel addressee;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status = FriendshipStatus.REQUEST;

    public enum FriendshipStatus {
        ACCEPTED, REQUEST
    }
}
