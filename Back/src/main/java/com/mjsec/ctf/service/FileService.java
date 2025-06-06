package com.mjsec.ctf.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.io.FileNotFoundException;
@Service
public class FileService {

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String store(MultipartFile multipartFile) throws IOException {
        InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();

        String uuid = UUID.randomUUID().toString();
        String ext = multipartFile.getContentType();

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

        String imgUrl = "https://storage.googleapis.com/" + bucketName + "/" + uuid;

        if (multipartFile.isEmpty()) {
            imgUrl = null;
        } else {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                    .setContentType(ext).build();

            Blob blob = storage.create(blobInfo, multipartFile.getInputStream());
        }

        return imgUrl;
    }
    // 파일 다운로드 기능
    public byte[] download(String fileId) throws IOException {
        
        InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

        
        Blob blob = storage.get(bucketName, fileId);
        if (blob == null) {
            throw new FileNotFoundException("File not found with id: " + fileId);
        }

        // 파일의 내용을 byte 배열로 반환
        return blob.getContent();
    }
}
