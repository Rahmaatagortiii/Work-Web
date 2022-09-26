package com.wellbeignatwork.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import com.wellbeignatwork.backend.config.NotificationConfig.FCMInitializer;
import com.wellbeignatwork.backend.exceptions.Event.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FirebaseStorage {
    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;
    private final String BUCKETNAME = "web-notifications-53dec.appspot.com";
    private StorageOptions storageOptions;
    private final String PROJECTID = "web-notifications-53dec";


    private StorageOptions getStorageOptions() throws IOException {
        StorageOptions storageOptions;

        storageOptions = StorageOptions.newBuilder()
                .setProjectId(PROJECTID)
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
        return storageOptions;
    }


    public String uploadFile(MultipartFile multipartFile) throws IOException {
        //Check if the file uploaded is image type
        try (InputStream input = multipartFile.getInputStream()) {
            try {
                ImageIO.read(input).toString();
                File file = convertMultiPartToFile(multipartFile);

                String extension = FilenameUtils.getExtension(file.getName());
                Path filePath = file.toPath();

                String fileName = generateFileName(multipartFile);
                Storage storage = getStorageOptions().getService();

                BlobId blobId = BlobId.of(BUCKETNAME, fileName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/" + extension).build();
                Blob blob = storage.create(blobInfo, Files.readAllBytes(filePath));
                log.info("File " + filePath + " uploaded to bucket " + BUCKETNAME + " as " + fileName + " blobId " + blobId);
                return fileName;
            } catch (Exception e) {

                throw new BadRequestException("File uploaded must be of type image only");

            }
        }
    }

    public boolean deleteFile(String fileName) {
        Storage storage = storageOptions.getService();
        BlobId blobId = BlobId.of(BUCKETNAME, fileName);
        return storage.delete(blobId);
    }

    public ResponseEntity<Object> getFile(String fileName, HttpServletRequest request) throws Exception {
        Storage storage = storageOptions.getService();

        Blob blob = storage.get(BlobId.of(BUCKETNAME, fileName));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);

        byte[] content;
        log.info("File retrieved successfully.");

        content = IOUtils.toByteArray(inputStream);

        final ByteArrayResource byteArrayResource = new ByteArrayResource(content);

        return ResponseEntity
                .ok()
                .contentLength(content.length)
                .header("Content-type", "image/jpg")
                //uncomment this in case we want to download the file
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(byteArrayResource);

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }
}
