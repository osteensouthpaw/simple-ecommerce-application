package com.omega.simpleecommerceapplication.file;

import com.omega.simpleecommerceapplication.product.Product;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.System.*;

@Service
@Slf4j
public class FileStorageService {
    @Value("${spring.application.file.upload.file-upload-path}")
    private String fileUploadPath;

    public String savePicture(@NonNull MultipartFile sourceFile,
                              @NonNull Product product) {
        String category = product.getProductCategory().getCategoryName().toLowerCase();
        String productName = product.getProductName().toLowerCase();
        final String fileUploadSubPath = category + File.separator + productName;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    private String uploadFile(MultipartFile sourceFile, String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + File.separator + fileUploadSubPath;

        //create target directory if it doesn't exist
        File targetPath = new File(finalUploadPath);
        if (!targetPath.exists()) {
            boolean targetPaths = targetPath.mkdirs();
            if (!targetPaths) {
                log.error("Unable to create target path {}", targetPath);
                return null;
            }
        }

        String fileExtension = getFileExtension(Objects.requireNonNull(sourceFile.getOriginalFilename()));
        fileUploadPath = finalUploadPath + File.separator + currentTimeMillis() + "." + fileExtension;

        try {
            Path path = Paths.get(fileUploadPath);
            Path savedFilePath = Files.write(path, sourceFile.getBytes());
            log.info("file has been saved to {}", savedFilePath);
            return savedFilePath.toString();
        } catch (IOException e) {
            log.error("unable to save file {}",e.getMessage());
        }
        return null;
    }

    public byte[] downloadFile(String fileUrl) {
        if (StringUtils.isBlank(fileUrl))
            return null;
        try {
            Path path = new File(fileUrl).toPath();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.warn("No file found in path {} ", e.getMessage());
        }
        return null;
    }

    private String getFileExtension(String filename) {
        if (filename.isBlank() || filename.isEmpty())
            return null;
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1)
            return "";

        return filename.substring(lastDotIndex + 1).toLowerCase();
    }
}
