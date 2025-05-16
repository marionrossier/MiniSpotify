package Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

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
            System.err.print("Error during the saving action for : " + e.getMessage());
        }
    }

    //TODO : retirer si pas de bug
//    public String createWritableDirectory(String directoryPath) {
//        try {
//            Path targetPath = Paths.get(userHome, fileName, directoryPath);
//            Files.createDirectories(targetPath);
//
//            return targetPath.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("Error creating folder : " + userHome + fileName + "\n Message : " + e.getMessage());
//        }
//    }

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

            // Charger les ressources du dossier
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


