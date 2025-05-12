package clientSide.services;

import serverSide.repositoriesPattern.ArtistLocalRepository;

public class ArtistService {
    private final ArtistLocalRepository artistLocalRepository;

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
