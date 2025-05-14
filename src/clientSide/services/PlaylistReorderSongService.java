package clientSide.services;

import serverSide.entities.Playlist;
import serverSide.repositoriesPattern.IPlaylistRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PlaylistReorderSongService {

    private final Scanner scanner;
    private final IPlaylistRepository playlistLocalRepository;

    public PlaylistReorderSongService(ToolBoxService toolBoxService, Scanner scanner) {
        this.playlistLocalRepository = toolBoxService.playlistLocalRepository;
        this.scanner = scanner;

    }

    public void reorderSongsInPlaylist(int playlistId, PlaylistServices playlistServices) {
        Playlist playlist = playlistServices.getPlaylistById(playlistId);
        LinkedList<Integer> newOrder = collectNewOrderFromUser(playlist);

        completeWithRemainingSongs(playlist, newOrder);
        playlist.setListSongsId(newOrder);
        playlistLocalRepository.savePlaylist(playlist);

        printSuccessMessage(playlist, newOrder);
    }

    private LinkedList<Integer> collectNewOrderFromUser(Playlist playlist) {
        LinkedList<Integer> newOrder = new LinkedList<>();
        int songIndex;

        System.out.println("Enter the new order of songs (one by one). Press \"x\" to finish:");

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("x")) {
                break;
            }

            try {
                songIndex = Integer.parseInt(input) - 1;
                if (isValidIndex(songIndex, playlist)) {
                    int songId = playlist.getPlaylistSongsListWithId().get(songIndex);
                    if (!newOrder.contains(songId)) {
                        newOrder.add(songId);
                    } else {
                        System.err.println("This song is already in the new order. Try again.");
                    }
                } else {
                    System.err.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number or \"x\" to finish.");
            }
        }

        return newOrder;
    }

    private boolean isValidIndex(int index, Playlist playlist) {
        return index >= 0 && index < playlist.getSize();
    }

    private void completeWithRemainingSongs(Playlist playlist, List<Integer> newOrder) {
        List<Integer> allSongs = playlist.getPlaylistSongsListWithId();
        for (Integer songId : allSongs) {
            if (!newOrder.contains(songId)) {
                newOrder.add(songId);
            }
        }
    }

    private void printSuccessMessage(Playlist playlist, List<Integer> newOrder) {
        if (newOrder.size() < playlist.getSize()) {
            System.out.println("Playlist reordered successfully with remaining songs added at the end!");
        } else {
            System.out.println("Playlist reordered successfully!");
        }
    }

}
