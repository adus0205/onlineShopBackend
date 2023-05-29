package pl.szupke.onlineShop.admin.product.service;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class ExsistingFileRenameUtils {

    public static String renameIfExists(Path uploadDir, String filename) {
        if (Files.exists(uploadDir.resolve(filename))){
            //tutaj zmień nazwę
            return renamedAndCheck(uploadDir, filename);
        }
        return  filename;
    }

    private static String renamedAndCheck(Path uploadDir, String filename) {
        String newName = renameFileName(filename);
        if (Files.exists(uploadDir.resolve(newName))){
            newName = renamedAndCheck(uploadDir, newName);
        }
        return newName;
    }

    private static String renameFileName(String filename) {
        String name = FilenameUtils.getBaseName(filename);
        String[] split = name.split("-(?=[0-9]+$)");
        int counter = split.length > 1 ? Integer.parseInt(split[1]) + 1 : 1;
        return split[0] + "-" + counter + "." + FilenameUtils.getExtension(filename);
    }
}
