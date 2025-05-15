package data.jsons;

import serverSide.entities.Artist;
import serverSide.repoLocal.ArtistLocalRepository;
import org.junit.jupiter.api.*;
import utilsAndFakes.CommuneMethods;
import utilsAndFakes.Initializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtistLocalRepoTest{

    private File tempFile;
    private ArtistLocalRepository repo;
    private CommuneMethods communeMethods;
    private Initializer initializer;

    public ArtistLocalRepoTest(){
    }

    @BeforeEach
    void setup() throws IOException {
        tempFile = Files.createTempFile("artist", ".json").toFile();
        repo = new ArtistLocalRepository(tempFile.getAbsolutePath());
        communeMethods = new CommuneMethods();
        initializer = communeMethods.initializer;
    }

    @AfterEach
    void tearDown() {
        initializer.cleanUp();
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
