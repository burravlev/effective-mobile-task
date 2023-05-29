package com.burravlev.task.files.repository;

import com.burravlev.task.files.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Image, Long> {
    @Query("FROM Image i WHERE i.id IN :ids")
    List<Image> findAll(@Param("ids") List<Long> ids);
}
