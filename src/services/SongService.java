package services;

import data.entities.Song;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SongService {

    Scanner in = new Scanner(System.in);
    private final Icon icon = new Icon();
    private final SongRepository songRepository = new SongRepository();
    private final PageService pageService = new PageService();
    private final PrintService printService = new PrintService();
    private final PlaylistServices playlistServices = new PlaylistServices(new PlaylistRepository());

    // Constructor
    public SongService(SongRepository songRepo) {
    }

    public void searchSong(String input, String type, int pageId) {
        LinkedList<Integer> foundedSongs;

        switch (type) {
            case "byTitle" -> foundedSongs = searchByTitle(input);
            case "byArtist" -> foundedSongs = searchByArtist(input);
            case "byGender" -> foundedSongs = searchByGender(input);
            default -> {
                System.err.println("Invalid search type: " + type);
                pageService.goBack(pageId);
                return;
            }
        }

        if (foundedSongs.isEmpty()) {
            System.out.println("No songs found.");
            pageService.goBack(pageId);
            return;
        }

        searchSongManager(foundedSongs, input);
    }

    public void searchSongManager (LinkedList<Integer> foundedSongs, String input){
        printService.printSongFound(foundedSongs, input);
        LinkedList<Integer> chosenSongs = chooseFoundedSongs(foundedSongs);

        playlistServices.createTemporaryPlaylist(chosenSongs);

//        //TODO : a valider is nécessaire....On passe sur la page des playlist et on la séléection...
//        //initialisation du current song cookie
//        playlistServices.setCurrentSongId(playlistServices.playlistRepository
//                .getPlaylistById(playlistServices.getTemporaryPlaylistId()).getPlaylistSongsListWithId().getFirst());
    }

    public LinkedList<Integer> searchByTitle(String songTitle){

        LinkedList<Song> songsByTitle;

        if (songTitle == null || songTitle.isEmpty()) {
            System.out.println("No result.");
            return new LinkedList<>();
        }
        else {
            songsByTitle = songRepository.getSongsByTitle(songTitle);
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
            songsByArtist = songRepository.getSongsByArtist(artistName);
        }

        return listSongToListInt(songsByArtist);
    }

    private LinkedList<Integer> searchByGender(String genderName) {
        LinkedList<Song> songsByGender;

        if (genderName == null || genderName.isEmpty()) {
            System.out.println("No result.");
            return new LinkedList<>();
        }
        else {
            songsByGender = songRepository.getSongsByArtist(genderName);
        }

        return listSongToListInt(songsByGender);
    }

    private static LinkedList<Integer> listSongToListInt(LinkedList<Song> songsByTitle) {
        LinkedList<Integer> songsByTitleId = new LinkedList<>();
        if (songsByTitle.isEmpty()) {
            return songsByTitleId;
        }
        for (Song song : songsByTitle) {
            songsByTitleId.add(song.getSongId());
        }
        return songsByTitleId;
    }

    public LinkedList<Integer> chooseFoundedSongs(List<Integer> foundedSongs){
        System.out.println("Choose your songs by entering their number and press \"enter\" between each song." + icon.lineBreak+
                "End selection with an \"x\"." + icon.lineBreak);
        System.out.print("Your selection : ");
        String input;
        LinkedList<Integer> selectedSongsIndex = new LinkedList<>();

        while (true) {
            input = pageService.gotAnInput(in.nextLine());
            if (input.equals("x")) {
                break;
            }
            if (input.equals("0")){
                pageService.goBack(pageService.getMenuPages().peek());
            }
            try {
                int songIndex = Integer.parseInt(input) - 1;
                if (songIndex >= 0 && songIndex < foundedSongs.size()) {
                    selectedSongsIndex.add(songIndex);
                } else {
                    System.err.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number or \"x\" to exit.");
            }
        }

        LinkedList<Integer> selectedSongs = new LinkedList<>();

        for (int index : selectedSongsIndex) {
            if (index < foundedSongs.size()) {
                selectedSongs.add(foundedSongs.get(index));
            }
        }
        return selectedSongs;
    }
}
