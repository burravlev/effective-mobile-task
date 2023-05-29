package com.burravlev.task.content.repository;

import com.burravlev.task.content.domail.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
