package clientSide.services;

import commun.IArtistRepository;

public class ArtistService {
    private final IArtistRepository artistLocalRepository;

    public ArtistService(ToolBoxService toolBoxService) {
        this.artistLocalRepository = toolBoxService.artistLocalRepository;
    }

    public String getArtisteNameById (int artistId){
        return artistLocalRepository.getArtistById(artistId).getArtistName();
    }

    public String getArtistNameBySong(int songId) {
        return artistLocalRepository.getArtistBySongId(songId).getArtistName();
    }
}
