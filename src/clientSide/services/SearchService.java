package clientSide.services;

import serverSide.entities.MusicGender;
import serverSide.entities.Playlist;
import serverSide.entities.Song;
import serverSide.repositories.ArtistLocalRepository;
import serverSide.repositories.SongLocalRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SearchService {

    Scanner scanner = new Scanner(System.in);
    private final Icon icon = new Icon();
    public final SongService songService;
    public final ArtistLocalRepository artistLocalRepository;
    private final PrintService printService;

    // Constructor
    public SearchService(SongService songService, ArtistLocalRepository artistLocalRepository, PrintService printService) {
        this.songService = songService;
        this.artistLocalRepository = artistLocalRepository;
        this.printService = printService;
    }

    public void searchSong(String input, String type, int pageId, PageService pageService, PlaylistServices playlistServices) {
        LinkedList<Integer> foundedSongs;

        switch (type) {
            case "byTitle" :
                foundedSongs = searchByTitle(input);
                break;
            case "byArtist" :
                foundedSongs = searchByArtist(input);
                break;
            case "byGender" :
                MusicGender gender = MusicGender.valueOf(input);
                foundedSongs = searchByGender(gender);
                break;
            default :
                System.err.println("Invalid search type: " + type);
                pageService.goBack(pageId);
                return;
        }
        if (foundedSongs.isEmpty()) {
            System.out.println("No songs found.");
            pageService.goBack(pageId);
            return;
        }
        printService.printSongFound(foundedSongs, input, this);
        LinkedList<Integer> chosenSongs = chooseFoundedSongs(foundedSongs, pageService);

        playlistServices.createTemporaryPlaylist(chosenSongs, playlistServices.getPlaylistStatus());
    }

    public LinkedList<Integer> searchByTitle(String songTitle){

        LinkedList<Song> songsByTitle;

        if (songTitle == null || songTitle.isEmpty()) {
            System.out.println("No result.");
            return new LinkedList<>();
        }
        else {
            songsByTitle = songService.songLocalRepository.getSongsByTitle(songTitle);
        }

        return listSongToListInt(songsByTitle);
    }

    private LinkedList<Integer> searchByArtist(String artistName) {
        LinkedList<Song> songsByArtist;

        if (artistName == null || artistName.isEmpty()) {
            System.out.println("No result.");
            return new LinkedList<>();
        }
        else {
            songsByArtist = songService.songLocalRepository.getSongsByArtist(artistName, artistLocalRepository);
        }

        return listSongToListInt(songsByArtist);
    }

    private LinkedList<Integer> searchByGender(MusicGender genderName) {
        LinkedList<Song> songsByGender;

        if (genderName == null) {
            System.out.println("No result.");
            return new LinkedList<>();
        }
        else {
            songsByGender = songService.songLocalRepository.getSongsByGender(genderName);
        }

        return listSongToListInt(songsByGender);
    }

    private LinkedList<Integer> listSongToListInt(LinkedList<Song> songsByTitle) {
        LinkedList<Integer> songsByTitleId = new LinkedList<>();
        if (songsByTitle.isEmpty()) {
            return songsByTitleId;
        }
        for (Song song : songsByTitle) {
            songsByTitleId.add(song.getSongId());
        }
        return songsByTitleId;
    }

    private LinkedList<Integer> listPlaylistToListInt(List<Playlist> playlists) {
        LinkedList<Integer> playlistById = new LinkedList<>();
        if (playlists.isEmpty()) {
            return playlistById;
        }
        for (Playlist playlist : playlists) {
            playlistById.add(playlist.getPlaylistId());
        }
        return playlistById;
    }

    public LinkedList<Integer> chooseFoundedSongs(List<Integer> foundedSongs, PageService pageService){
        LinkedList<Integer> selectedSongsIndex = new LinkedList<>();
        System.out.println("Choose your songs by entering their number and press \"enter\" between each song." + icon.lineBreak+
                "End selection with an \"x\"." + icon.lineBreak);
        System.out.print("Your selection : ");

        loopIntInputValidation(pageService, selectedSongsIndex, foundedSongs.size());

        //Transform songIndexes in songIds
        LinkedList<Integer> selectedSongs = new LinkedList<>();

        for (int index : selectedSongsIndex) {
            if (index < foundedSongs.size()) {
                selectedSongs.add(foundedSongs.get(index));
            }
        }
        return selectedSongs;
    }

    public LinkedList<Integer> chooseFoundedPlaylist(List<Playlist> playlist, PageService pageService){
        LinkedList<Integer> selectedPlaylistIndex = new LinkedList<>();
        loopIntInputValidation(pageService, selectedPlaylistIndex, playlist.size());
        return selectedPlaylistIndex;
    }

    private void loopIntInputValidation(PageService pageService, LinkedList<Integer> selectedSongsIndex, int size) {
        String input;
        while (true) {
            input = pageService.gotAnInput(scanner.nextLine());
            if (input.equals("x")) {
                break;
            }
            if (input.equals("0")){
                pageService.goBack(pageService.getMenuPages().peek());
            }
            try {
                int songIndex = Integer.parseInt(input) - 1;
                if (songIndex >= 0 && songIndex < size) {
                    selectedSongsIndex.add(songIndex);
                } else {
                    System.err.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number or \"x\" to exit.");
            }
        }
    }
}
