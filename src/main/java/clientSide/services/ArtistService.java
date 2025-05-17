package clientSide.services;

import commun.*;

public class ArtistService {
    private final IArtistRepository artistLocalRepository;

    public ArtistService(ToolBoxService toolBoxService) {
        this.artistLocalRepository = toolBoxService.artistLocalRepository;
    }

    public String getArtistNameBySong(int songId) {
        return artistLocalRepository.getArtistBySongId(songId).getArtistName();
    }
}
