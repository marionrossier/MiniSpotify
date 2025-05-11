package clientSide.services;

import serverSide.repositories.ArtistLocalRepository;

public class ArtistService {
    private final ArtistLocalRepository artistLocalRepository;

    public ArtistService(ServiceToolBox serviceToolBox) {
        this.artistLocalRepository = serviceToolBox.artistLocalRepository;
    }

    public String getArtisteNameById (int artistId){
        return artistLocalRepository.getArtistById(artistId).getArtistName();
    }

    public String getArtistNameBySong(int songId) {
        return artistLocalRepository.getArtistBySongId(songId).getArtistName();
    }
}
