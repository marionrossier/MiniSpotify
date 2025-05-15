package clientSide.repoFront;

import middle.IArtistRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import serverSide.entities.Artist;
import utilsAndFakes.CommuneMethods;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrontArtistRepoSocketTest {

    static IArtistRepository artistRepo;
    static CommuneMethods commune;

    public FrontArtistRepoSocketTest() throws IOException {
    }

    @BeforeAll
    static void setup() throws IOException {
        commune = new CommuneMethods() {};
        artistRepo = commune.startServerAndInitRepo(FrontArtistRepo::new);
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
