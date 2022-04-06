package com.knuipalab.dsmp.service.storage;

import com.knuipalab.dsmp.domain.storage.Storage;
import com.knuipalab.dsmp.domain.storage.StorageRepository;
import com.knuipalab.dsmp.dto.storage.StorageRequestDto;
import com.knuipalab.dsmp.httpResponse.error.ErrorCode;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.EmptyFileBadRequestException;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.FileIOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
public class FileSystemStorageService implements StorageService {

    private final StorageRepository storageRepository;

    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties properties,StorageRepository storageRepository ) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.storageRepository = storageRepository;
    }

    @Override
    public void uploadFile(String projectId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new EmptyFileBadRequestException(ErrorCode.EMPTY_FILE_BAD_REQUEST);
        }
        Path destinationFile = this.rootLocation
                .resolve(Paths.get(String.format("%s/%s",projectId,file.getOriginalFilename())))
                .normalize().toAbsolutePath();

        try (InputStream inputStream = file.getInputStream()) {
            if (!Files.exists(destinationFile.getParent())) {
                Files.createDirectories(destinationFile.getParent());
            }
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileIOException(ErrorCode.FILE_IO_EXCEPTION);
        }

        storageRepository.save(Storage.builder()
                .projectId(projectId)
                .fileOriginalName(file.getOriginalFilename())
                .filePath(destinationFile.toString())
                .build());

    }

    @Override
    public void deleteAll(String projectId) {
        Path projectPath = this.rootLocation.resolve(Paths.get(projectId)).normalize().toAbsolutePath();
        try {
            FileSystemUtils.deleteRecursively(projectPath);
        } catch (IOException e) {
            throw new FileIOException(ErrorCode.FILE_IO_EXCEPTION);
        }
    }


}
