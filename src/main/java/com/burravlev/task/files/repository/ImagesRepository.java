package com.burravlev.task.files.repository;

import com.burravlev.task.files.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Image, Long> {
}
