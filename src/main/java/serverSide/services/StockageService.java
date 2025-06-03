package serverSide.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.*;
import java.util.*;

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
            System.err.println("Error during the saving action for : " + e.getMessage());
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

    public void copyMp3FilesFromResources() {
        if (isRunningUnderTest()) {
            System.out.println("Skipping mp3 extraction during test execution.");
            return;
        }

        try {
            // Destination folder = same as old implementation
            Path outputDir = Paths.get(System.getProperty("user.home"), "MiniSpotifyFlorentMarion", "songsfiles");
            Files.createDirectories(outputDir);

            // Load the index.txt from classpath
            InputStream indexStream = StockageService.class.getClassLoader()
                    .getResourceAsStream("songsfiles/index.txt");

            if (indexStream == null) {
                System.err.println("⚠ index.txt not found in songs/ folder inside resources.");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(indexStream));
            String fileName;
            while ((fileName = reader.readLine()) != null) {
                InputStream mp3Stream = StockageService.class.getClassLoader()
                        .getResourceAsStream("songsfiles/" + fileName);

                if (mp3Stream == null) {
                    System.err.println("Missing file in JAR: " + fileName);
                    continue;
                }

                Path targetPath = outputDir.resolve(fileName);

                if (Files.exists(targetPath)) {
                    continue;
                }

                Files.copy(mp3Stream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error copying mp3 files from resources", e);
        }
    }

    private boolean isRunningUnderTest() {
        // Detect Maven Surefire (unit test runner) via system properties
        return System.getProperty("surefire.test.class.path") != null;
    }
}


