package services;

import data.entities.Song;
import data.jsons.SongRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SongService {

    Icons icons = new Icons();
    Scanner in = new Scanner(System.in);
    String linebreak = "\n";
    SongRepository songRepository = new SongRepository();

    public SongService() {
        // Constructor
    }

    public List<Integer> searchSongByTitle(String songTitle){

        List<Song> songsByTitle = songRepository.getSongsByTitle(songTitle);
        if (songsByTitle.isEmpty()) {
        //TODO : implémenter le stack des pages ouvertes ici pour pourvoir revenir en arrière avec le 0!
        }
        List<Integer> songsByTitleId = new ArrayList<>();
        for (Song song : songsByTitle) {
            songsByTitleId.add(song.getSongId());
        }
        return songsByTitleId;
    }

    public void printSongFound (List<Integer> songs, String info){
        System.out.println("Songs found with information : " + info);
        printSongList (songs);
    }

    public void printSongList (List<Integer> songs){
        int i = 1;
        for (Integer song : songs) {
            System.out.println(i + ". " + songRepository.getSongById(song).getSongName());
            i++;
        }
    }

    public List<Integer> chooseFoundedSongs(List<Integer> foundedSongs){
        System.out.println("Choose your songs by entering their number and press \"enter\" between each song." + linebreak+
                "End selection with an \"x\"." + linebreak +
                " Your selection : ");
        String input;
        List<Integer> selectedSongsIndex = new ArrayList<>();

        while (true) {
            input = in.nextLine();
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

        List<Integer> selectedSongs = new ArrayList<>();

        for (int index : selectedSongsIndex) {
            if (index < foundedSongs.size()) {
                selectedSongs.add(foundedSongs.get(index));
            }
        }
        return selectedSongs;
    }
}
