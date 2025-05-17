package commun;

import serverSide.entities.Artist;

import java.util.List;

public interface IArtistRepository {

    List<Artist> getAllArtists();
    void addArtist(Artist artist);
    Artist getArtistById(int artistId);
    void saveArtist(Artist artist);
    Artist getArtistByName(String name);
    Artist getArtistBySongId(int songId);
}