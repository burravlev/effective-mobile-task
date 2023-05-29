package com.burravlev.task.content.service;

import com.burravlev.task.content.domain.model.Image;
import com.burravlev.task.content.exception.FileProcessingException;
import com.burravlev.task.content.repository.ImageRepository;
import com.burravlev.task.content.util.FileNameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final ImageRepository repository;

    @Value("${application.files.dir}")
    private String outputDir;

    @Override
    public Image save(MultipartFile multipartFile) {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

        File file = new File(outputDir + FileNameGenerator.generate() + "." + extension);
        try {
            multipartFile.transferTo(file);
            return Image.builder()
                    .url("/api/v1/img/" + file.getName())
                    .filename(file.getName())
                    .build();
        }  catch (IOException e) {
            System.out.println(e.getMessage());
            throw new FileProcessingException(e.getMessage());
        }
    }

    @Override
    public ByteArrayResource get(String imageName) {
        try {
            return new ByteArrayResource(Files.readAllBytes(Paths.get(
                    outputDir + imageName
            )));
        } catch (Exception e) {
            throw new FileProcessingException(e.getMessage());
        }
    }
}
