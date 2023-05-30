package com.burravlev.task.files.service;

import com.burravlev.task.files.domain.entity.ImageEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageStorageService {

    List<ImageEntity> save(List<MultipartFile> files);

    ImageEntity getById(Long id);

    ByteArrayResource getResource(String name);

    List<ImageEntity> findAll(List<Long> ids);
}
