package com.burravlev.task.files.service;

import com.burravlev.task.files.domain.entity.Image;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ImageStorageService {

    List<Image> save(List<MultipartFile> files);

    Image getById(Long id);

    ByteArrayResource getResource(String name);
}
