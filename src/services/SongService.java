package services;

import data.entities.Song;
import data.jsons.SongRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SongService {

    Scanner in = new Scanner(System.in);
    private final Icon icon = new Icon();
    private final SongRepository songRepository = new SongRepository();
    private final PageService pageService = new PageService();

    // Constructor
    public SongService(SongRepository songRepo) {
    }

    public LinkedList<Integer> searchSongByTitle(String songTitle){

        LinkedList<Song> songsByTitle = songRepository.getSongsByTitle(songTitle);
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
