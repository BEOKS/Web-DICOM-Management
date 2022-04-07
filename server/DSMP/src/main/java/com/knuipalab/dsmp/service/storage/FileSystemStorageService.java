package com.knuipalab.dsmp.service.storage;

import com.knuipalab.dsmp.domain.storage.StorageRepository;
import com.knuipalab.dsmp.httpResponse.error.ErrorCode;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.EmptyFileBadRequestException;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.FileIOException;
import com.knuipalab.dsmp.httpResponse.error.handler.exception.FileNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

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

    }

    @Override
    public void deleteAll(String projectId) {
        Path projectPath = this.rootLocation.resolve(Paths.get(projectId)).normalize().toAbsolutePath();
        try {
            FileSystemUtils.deleteRecursively(projectPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileIOException(ErrorCode.FILE_IO_EXCEPTION);
        }
    }

    @Override
    public void deleteByFileName(String projectId, String fileName) {
        Path filePath = this.rootLocation
                .resolve(Paths.get(String.format("%s/%s",projectId,fileName)))
                .normalize().toAbsolutePath();
        File file = new File(filePath.toString());
        if(file.exists()){
            file.delete();
        }
        else {
            throw new FileNotFoundException(ErrorCode.FILE_NOT_FOUND);
        }
    }

    @Override
    public List<String> getFileList(String projectId) {
        Path projectPath = this.rootLocation.resolve(Paths.get(projectId)).normalize().toAbsolutePath();
        try {
            List<String> files = Files.walk(projectPath)
                    .filter( path -> !path.equals(projectPath))
                    .map( path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            return files;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new FileIOException(ErrorCode.FILE_IO_EXCEPTION);
        }
    }

    @Override
    public void serveFile(String projectId, String fileName, HttpServletRequest request, HttpServletResponse response) {
        // globals.properties
        Path filePath = this.rootLocation
                .resolve(Paths.get(String.format("%s/%s",projectId,fileName)))
                .normalize().toAbsolutePath();

        File file = new File(filePath.toString());

        if(!file.exists()){
            throw new FileNotFoundException(ErrorCode.FILE_NOT_FOUND);
        }

        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileIOException(ErrorCode.FILE_IO_EXCEPTION);
        }

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");

        try {
            if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
                //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
                fileName = URLEncoder.encode(file.getName(),"UTF-8").replaceAll("\\+", "\\ ");

            } else {
                //나머지 브라우저에서 인코딩
                fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1").replaceAll("\\+", "%20");

            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octer-stream");
        //다운로드와 다운로드될 파일이름
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
        //파일복사
        try {
            FileCopyUtils.copy(in, response.getOutputStream());
            in.close();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileIOException(ErrorCode.FILE_IO_EXCEPTION);
        }

    }

}
