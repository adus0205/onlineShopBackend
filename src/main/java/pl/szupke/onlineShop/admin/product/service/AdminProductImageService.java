package pl.szupke.onlineShop.admin.product.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.szupke.onlineShop.admin.common.utils.SligyfyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AdminProductImageService {
    @Value("${app.uploadDir}")
    private String uploadDir;
    public String uploadImage(String filename, InputStream inputStream){
        String newFileName = SligyfyUtils.slugifyFileName(filename);
        newFileName = ExsistingFileRenameUtils.renameIfExists(Path.of(uploadDir),filename);
        Path filePath = Paths.get(uploadDir).resolve(newFileName);
        try(OutputStream outputStream = Files.newOutputStream(filePath)) {
            inputStream.transferTo(outputStream);
        }catch(IOException e) {
            throw new RuntimeException("Nie można zapisać pliku", e);
        }
        return newFileName;
    }

    public Resource serveFiles(String filename){
        FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
        return fileSystemResourceLoader.getResource(uploadDir + filename);
    }
}
