package data.jsons;

import serverSide.entities.Artist;
import serverSide.repositories.ArtistLocalRepository;
import org.junit.jupiter.api.*;
import utilsAndFakes.CommuneMethods;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtistLocalRepositoryTest extends CommuneMethods {

    private File tempFile;
    private ArtistLocalRepository repo;

    public ArtistLocalRepositoryTest() throws IOException {
    }

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("artist", ".json").toFile();
        repo = new ArtistLocalRepository(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void addArtist_shouldSaveTheArtist() {
        // Arrange
        Artist artist = new Artist("Test Artist");

        // Act
        repo.addArtist(artist);

        // Assert
        List<Artist> artists = repo.getAllArtists();
        assertEquals(1, artists.size());
        assertEquals("Test Artist", artists.get(0).getArtistName());
    }

    @Test
    void deleteArtistById_shouldRemoveTheArtist() {
        // Arrange
        Artist artistOne = new Artist("Artist One");
        Artist artistTwo = new Artist("Artist Two");
        repo.addArtist(artistOne);
        repo.addArtist(artistTwo);

        // Act
        repo.deleteArtistById(artistOne.getArtistId());

        // Assert
        List<Artist> result = repo.getAllArtists();
        assertEquals(1, result.size());
        assertEquals(artistTwo.getArtistId(), result.get(0).getArtistId());
    }

    @Test
    void getArtistById_shouldFindTheArtist() {
        // Arrange
        Artist artist = new Artist("Test Artist");
        repo.addArtist(artist);

        // Act
        Artist result = repo.getArtistById(artist.getArtistId());

        // Assert
        assertNotNull(result);
    }

    @Test
    void updateArtist_shouldUpdateTheArtist() {
        // Arrange
        Artist artist = new Artist("Original Name");
        repo.addArtist(artist);

        // Act
        artist.setArtistName("Updated Name");
        repo.updateArtist(artist);

        // Assert
        Artist result = repo.getArtistById(artist.getArtistId());
        assertEquals("Updated Name", result.getArtistName());
    }

    @Test
    void getArtistByName_shouldFindTheArtist() {
        // Arrange
        Artist artist = new Artist("Test Artist");
        repo.addArtist(artist);

        // Act
        Artist result = repo.getArtistByName("Test Artist");

        // Assert
        assertNotNull(result);
        assertEquals(artist.getArtistId(), result.getArtistId());
    }
}
