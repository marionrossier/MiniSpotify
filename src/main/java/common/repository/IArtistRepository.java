package common.repository;

import common.entities.Artist;

import java.util.List;

public interface IArtistRepository {

    List<Artist> getAllArtists();
    void addArtist(Artist artist);
    Artist getArtistById(int artistId);
    void updateOrInsertArtist(Artist artist);
    Artist getArtistByName(String name);
    Artist getArtistBySongId(int songId);
}