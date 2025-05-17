package clientSide.repoFront;

import commun.IArtistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverSide.entities.Artist;
import utilsAndFakes.TestHelper;
import utilsAndFakes.DependencyProvider;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrontArtistRepoSocketTest {

    private IArtistRepository artistRepo;
    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    @BeforeEach
    void setup() {
        testHelper = new TestHelper();
        dependencyProvider = testHelper.dependencyProvider;
        artistRepo = dependencyProvider.frontArtistRepo;
        dependencyProvider.populateLocalUsers();
        dependencyProvider.populateLocalArtist();

        testHelper.startServer();
    }

    @AfterEach
    void tearDown() {
        dependencyProvider.cleanUp();
    }

    @Test
    void getArtistById_shouldReturnExpectedArtist() {
        // Arrange
        int artistId = 960571432;

        // Act
        Artist artist = artistRepo.getArtistById(artistId);

        // Assert
        assertNotNull(artist);
        assertEquals(artistId, artist.getArtistId());
    }

    @Test
    void getArtistByName_shouldReturnExpectedArtist() {
        // Arrange
        String artistName = "Amy Winehouse";

        // Act
        Artist artist = artistRepo.getArtistByName(artistName);

        // Assert
        assertNotNull(artist);
        assertEquals(artistName, artist.getArtistName());
    }

    @Test
    void getAllArtists_shouldIncludeKnownArtist() {
        // Arrange

        // Act
        List<Artist> allArtists = artistRepo.getAllArtists();

        // Assert
        assertNotNull(allArtists);
        assertTrue(allArtists.stream().anyMatch(a -> a.getArtistName().equals("Amy Winehouse")));
    }

    @Test
    void getArtistBySongId_shouldReturnExpectedArtist() {
        // Arrange
        int songId = 1108071776; // Rehab

        // Act
        Artist artist = artistRepo.getArtistBySongId(songId);

        // Assert
        assertNotNull(artist);
        assertTrue(artist.getArtistSongsID().contains(songId));
    }
}
