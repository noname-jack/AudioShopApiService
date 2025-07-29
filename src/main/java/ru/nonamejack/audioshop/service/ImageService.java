package ru.nonamejack.audioshop.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.nonamejack.audioshop.exception.custom.AccessDeniedException;

import ru.nonamejack.audioshop.exception.custom.FileCreateException;
import ru.nonamejack.audioshop.exception.custom.IncorrectUserFileException;
import ru.nonamejack.audioshop.exception.custom.NotFoundException;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageService {


    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${app.images.base-url:http://localhost}")
    private String baseUrl;

    @Value("${app.images.path}")
    private String imagesPath;

    @Value("${app.images.not-found}")
    private String notFoundImagePath;


    public ResponseEntity<Resource> getImage(HttpServletRequest request){
        try {
            String requestPath = request.getRequestURI();
            String imagePath = requestPath.substring(requestPath.indexOf("/images/") + 8);

            imagePath = URLDecoder.decode(imagePath, StandardCharsets.UTF_8);

            Path basePath = Paths.get(imagesPath);
            Path fullPath = basePath.resolve(imagePath).normalize();

            if (!fullPath.startsWith(basePath)) {
                throw new AccessDeniedException("Попытка доступа к файлу вне разрешенной директории");
            }

            String contentType = Files.probeContentType(fullPath);
            if (contentType == null || !contentType.startsWith("image")) {
                throw new IncorrectUserFileException("Файл не является изображением");
            }
            Resource resource = new UrlResource(fullPath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return createDefaultImage();
            }
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        }
        catch (IOException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при чтении изображения");
        }
    }

    private ResponseEntity<Resource> createDefaultImage(){
        try {
            Path notFoundPath = Paths.get(imagesPath).resolve(notFoundImagePath);
            Resource resource = new UrlResource(notFoundPath.toUri());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
        }
        catch (IOException ex){
            throw new NotFoundException("Стандартный файл не найден или недоступен для чтения");
        }
    }

    public String buildImageUrl(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return buildDefaultImageUrl();
        }

        String cleanPath = imagePath.trim()
                .replaceAll("[\\n\\r\\t\\f\0]", "")
                .replace("\\", "/");



        if (cleanPath.startsWith("/")) {
            cleanPath = cleanPath.substring(1);
        }

        String encodedPath = Arrays.stream(cleanPath.split("/"))
                .map(segment -> URLEncoder.encode(segment, StandardCharsets.UTF_8))
                .collect(Collectors.joining("/"));

        return String.format("%s:%s/images/%s",
                baseUrl, serverPort, encodedPath);

    }

    private String buildDefaultImageUrl() {
        return String.format("%s:%s/images/%s",
                baseUrl, serverPort, notFoundImagePath);
    }


    public boolean imageExists(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return false;
        }

        try {
            String cleanPath = imagePath.startsWith("/") ? imagePath.substring(1) : imagePath;
            cleanPath = cleanPath.replace("\\", "/");

            Path basePath = Paths.get(System.getProperty("app.images.path", "images"));
            Path fullPath = basePath.resolve(cleanPath).normalize();

            if (!fullPath.startsWith(basePath.normalize())) {
                return false;
            }

            return Files.exists(fullPath) && Files.isReadable(fullPath);
        } catch (Exception e) {
            return false;
        }
    }

    public String saveImage(MultipartFile file, String folderName){
        if (file.isEmpty()){
            throw new IncorrectUserFileException("Файл пуст");
        }
        String contentType = file.getContentType();
        if (!contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Файл не является изображением");
        }
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        String newFilename = UUID.randomUUID().toString() + extension;

        Path basePath = Paths.get(imagesPath);
        Path folderPath = basePath.resolve(folderName);
        try{
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }
            Path filePath = folderPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            Path relativePath = basePath.relativize(filePath);
            return relativePath.toString().replace("\\", "/");
        }
        catch (IOException ex){
            throw new FileCreateException("Ошибка сохранения файла");
        }
    }

    public void deleteImage(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            System.err.println("Не удалось удалить файл: " + imagePath + ", ошибка: " + e.getMessage());
        }
    }
}