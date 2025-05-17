package commun;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static clientSide.services.PrintHelper.printLNError;

public class StockageService {

    String userHome = System.getProperty("user.home");
    String fileName = "MiniSpotifyFlorentMarion";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> List<T> loadFromJson(String filePath, TypeReference<List<T>> typeReference) {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, typeReference);
        }
        catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public <T> void saveToJson(String filePath, List<T> data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (IOException e) {
            printLNError("Error during the saving action for : " + e.getMessage());
        }
    }

    public void copyResourceToWritableLocation(String resourcePath){
        try {
            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (resourceStream == null) {
                throw new IllegalArgumentException("Resource not found : " + resourcePath);
            }

            // Define the destination path in a user directory
            Path targetPath = Paths.get(userHome, fileName,resourcePath);

            Files.createDirectories(targetPath.getParent());

            if (!Files.exists(targetPath)) {
                Files.copy(resourceStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error when copying file from resources : "+ e.getMessage());
        }
    }

    public void copyAllSongsToWritableLocation(String resourceFolderPath) {
        try {
            Path targetDirectory = Paths.get(userHome, fileName, "songsfiles");
            Files.createDirectories(targetDirectory);

            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourceFolderPath);
            if (resourceStream == null) {
                throw new IllegalArgumentException("Resource folder not found: " + resourceFolderPath);
            }

            File resourceFolder = new File(getClass().getClassLoader().getResource(resourceFolderPath).toURI());
            File[] files = resourceFolder.listFiles((dir, name) -> name.endsWith(".mp3"));

            if (files != null) {
                for (File file : files) {
                    Path targetPath = targetDirectory.resolve(file.getName());
                    Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error copying .mp3 files: " + e.getMessage());
        }
    }
}


