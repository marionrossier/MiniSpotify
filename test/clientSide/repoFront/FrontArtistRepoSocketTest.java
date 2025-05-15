package clientSide.repoFront;

import middle.IArtistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverSide.entities.Artist;
import utilsAndFakes.CommuneMethods;
import utilsAndFakes.Initializer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrontArtistRepoSocketTest {

    private IArtistRepository artistRepo;
    private CommuneMethods communeMethods;
    private Initializer initializer;

    @BeforeEach
    void setup() {
        communeMethods = new CommuneMethods();
        initializer = communeMethods.initializer;
        artistRepo = initializer.frontArtistRepo;
        initializer.populateLocalUsers();
        initializer.populateLocalArtist();

        communeMethods.startServer();
    }

    @AfterEach
    void tearDown() {
        initializer.cleanUp();
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
