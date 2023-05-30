package com.burravlev.task.post.domain.entity;

import com.burravlev.task.files.domain.entity.ImageEntity;
import com.burravlev.task.user.domain.entity.UserEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserEntity creator;
    private String header;
    private String message;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_content",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<ImageEntity> content = new ArrayList<>();
    @CreatedDate
    private LocalDateTime created;

    public void addImage(ImageEntity image) {
        this.content.add(image);
    }
}
