package com.burravlev.task.files.repository;

import com.burravlev.task.files.domain.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImagesRepository extends JpaRepository<ImageEntity, Long> {
    @Query("FROM ImageEntity i WHERE i.id IN :ids")
    List<ImageEntity> findAll(@Param("ids") List<Long> ids);

    @Query("FROM ImageEntity i WHERE i.url IN :urls")
    List<ImageEntity> findAllInUrls(@Param("urls") List<String> urls);
}
