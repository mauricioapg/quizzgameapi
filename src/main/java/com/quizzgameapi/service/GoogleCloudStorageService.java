package com.quizzgameapi.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GoogleCloudStorageService {

    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private final String bucketName = "quizzgame-images";

    public String uploadFile(MultipartFile file) throws IOException {
        // Configurar informações do blob
        String blobName = file.getOriginalFilename();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, blobName).build();

        // Fazer o upload
        storage.create(blobInfo, file.getBytes());

        // Retornar a URL pública
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, blobName);
    }
}
