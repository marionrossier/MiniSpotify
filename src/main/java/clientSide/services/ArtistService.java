package clientSide.services;

import common.repository.IArtistRepository;

public class ArtistService {
    private final IArtistRepository artistRepository;

    public ArtistService(ToolBoxService toolBoxService) {
        this.artistRepository = toolBoxService.artistRepository;
    }

    public String getArtistNameBySong(int songId) {
        return artistRepository.getArtistBySongId(songId).getArtistName();
    }
}
