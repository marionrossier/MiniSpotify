package clientSide.services;

import serverSide.entities.MusicGender;
import serverSide.entities.Song;

import java.util.*;

import static clientSide.services.PrintHelper.*;

public class SearchService {

    Scanner scanner = new Scanner(System.in);
    private final IconService icon = new IconService();
    private final SongService songService;
    private final PrintService printService;
    private final UserService userService;

    // Constructor
    public SearchService(SongService songService,
                         PrintService printService,
                         UserService userService) {
        this.songService = songService;
        this.printService = printService;
        this.userService = userService;
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
                printInfo("Invalid search type: " + type);
                pageService.goBack(pageId);
                return;
        }
        if (foundedSongs.isEmpty()) {
            System.err.println("No songs found.");
            pageService.goBack(pageId);
            return;
        }
        printService.printSongFound(foundedSongs, input);
        LinkedList<Integer> chosenSongs = chooseFoundedSongs(foundedSongs, pageService);

        playlistServices.createTemporaryPlaylist(chosenSongs, playlistServices.getPlaylistStatus());
    }

    public LinkedList<Integer> searchByTitle(String songTitle){

        LinkedList<Song> songsByTitle;

        if (songTitle == null || songTitle.isEmpty()) {
            System.err.println("No result.");
            return new LinkedList<>();
        }
        else {
            songsByTitle = songService.getSongByTitle(songTitle);
        }

        return listSongToListInt(songsByTitle);
    }

    private LinkedList<Integer> searchByArtist(String artistName) {
        LinkedList<Song> songsByArtist;

        if (artistName == null || artistName.isEmpty()) {
            System.err.println("No result.");
            return new LinkedList<>();
        }
        else {
            songsByArtist = songService.getSongsByArtist(artistName);
        }

        return listSongToListInt(songsByArtist);
    }

    private LinkedList<Integer> searchByGender(MusicGender genderName) {
        LinkedList<Song> songsByGender;

        if (genderName == null) {
            System.err.println("No result.");
            return new LinkedList<>();
        }
        else {
            songsByGender = songService.getSongsByGender(genderName);
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

    public LinkedList<Integer> chooseFoundedSongs(List<Integer> foundedSongs, PageService pageService){
        LinkedList<Integer> selectedSongsIndex = new LinkedList<>();
        printLNWhite("Choose your songs by entering their number and press \"enter\" between each song." + icon.lineBreak+
                "End selection with an \"x\"." + icon.lineBreak);
        printWhite("Your selection : ");

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

    public LinkedList<Integer> chooseFoundedPlaylist(List<Integer> playlist, PageService pageService){
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
                    printInfo("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                printInfo("Invalid input. Please enter a number or \"x\" to exit.");
            }
        }
    }

    public List<Integer> searchUserByPseudonyme (String pseudonym){
        List<Integer> usersId = new ArrayList<>();
        if (pseudonym == null || pseudonym.isEmpty()){
            System.err.println("No pseudonym given.");
        }
        else{
            usersId = userService.getUsersByPseudonym(pseudonym);
        }
        return usersId;
    }
}
