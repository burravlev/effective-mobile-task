package com.burravlev.task.token.model;

import com.burravlev.task.user.domain.entity.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TokenType type = TokenType.BEARER;
    private boolean revoked;
    private boolean expired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
