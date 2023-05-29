package com.burravlev.task.post.domain.entity;

import com.burravlev.task.files.domain.entity.Image;
import com.burravlev.task.user.domain.model.UserModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserModel creator;
    private String header;
    private String message;
    @ManyToMany
    @JoinTable(
            name = "post_content",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Image> content = new ArrayList<>();
    @CreatedDate
    private LocalDateTime created;

    public void addImage(Image image) {
        this.content.add(image);
    }
}
