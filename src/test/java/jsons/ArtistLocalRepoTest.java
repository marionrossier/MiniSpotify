package jsons;

import serverSide.entities.*;
import serverSide.repoLocal.*;
import org.junit.jupiter.api.*;
import utilsAndFakes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtistLocalRepoTest{

    private File tempFile;
    private ArtistLocalRepository repo;
    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    public ArtistLocalRepoTest(){
    }

    @BeforeEach
    void setup() throws IOException {
        tempFile = Files.createTempFile("artist", ".json").toFile();
        repo = new ArtistLocalRepository(tempFile.getAbsolutePath());
        testHelper = new TestHelper(45000);
        dependencyProvider = testHelper.dependencyProvider;
    }

    @AfterEach
    void tearDown() {
        dependencyProvider.cleanUp();
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
