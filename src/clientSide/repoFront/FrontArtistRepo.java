package clientSide.repoFront;

import serverSide.entities.Artist;
import middle.IArtistRepository;

import java.util.List;

public class FrontArtistRepo implements IArtistRepository {
    @Override
    public List<Artist> getAllArtists() {
        return List.of();
    }

    @Override
    public void addArtist(Artist artist) {

    }

    @Override
    public Artist getArtistById(int artistId) {
        return null;
    }

    @Override
    public void saveArtist(Artist artist) {

    }

    @Override
    public Artist getArtistByName(String name) {
        return null;
    }

    @Override
    public Artist getArtistBySongId(int songId) {
        return null;
    }
}
