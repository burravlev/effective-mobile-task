package com.burravlev.task.content.service;

import com.burravlev.task.content.domain.model.Image;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    Image save(MultipartFile multipartFile);

    ByteArrayResource get(String imageName);
}
