package com.burravlev.task.files.service;

import com.burravlev.task.exception.NotFoundException;
import com.burravlev.task.files.domain.entity.Image;
import com.burravlev.task.files.exception.FileProcessingException;
import com.burravlev.task.files.repository.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {
    private final ImagesRepository repository;
    @Value("${application.files.dir}")
    private String outputDir;
    private Path root;

    @PostConstruct
    public void init() throws Exception {
        this.root = Paths.get(outputDir);
        Files.createDirectories(root);
    }

    @Override
    public List<Image> save(List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            final Image image = new Image();
            if (file.getContentType().equals(Image.ImageType.PNG.getType()))
                image.setType(Image.ImageType.PNG);
            else if (file.getContentType().equals(Image.ImageType.JPEG.getType()))
                image.setType(Image.ImageType.JPEG);
            else throw new IllegalArgumentException("Image should be only png or jpeg");

            try {
                String name = UUID.randomUUID().toString();
                Files.copy(file.getInputStream(), this.root.resolve(name + image.getType().getExtension()));
                image.setFilename(name);
                images.add(image);
            } catch (Exception e) {

            }
        }
        return repository.saveAll(images);
    }

    @Override
    public Image getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found exception, image id: " + id));
    }

    @Override
    public ByteArrayResource getResource(String name) {
        try {
            return new ByteArrayResource(Files.readAllBytes(Paths.get(
                    outputDir + "/" + name
            )));
        } catch (Exception e) {
            System.out.println(e.getClass());
            throw new FileProcessingException("Internal server error while processing image");
        }
    }
}
