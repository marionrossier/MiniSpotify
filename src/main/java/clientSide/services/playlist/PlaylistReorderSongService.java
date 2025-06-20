package clientSide.services.playlist;

import clientSide.services.PlaylistServices;
import common.entities.Playlist;
import common.repository.IPlaylistRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static clientSide.services.PrintHelper.*;

public class PlaylistReorderSongService {

    public PlaylistReorderSongService() {}

    public void reorderSongsInPlaylist(int playlistId, PlaylistServices playlistServices,
                                       Scanner scanner, IPlaylistRepository playlistRepository) {
        Playlist playlist = playlistServices.getPlaylistById(playlistId);
        LinkedList<Integer> newOrder = collectNewOrderFromUser(playlist, scanner);

        completeWithRemainingSongs(playlist, newOrder);
        playlist.setListSongsId(newOrder);
        playlistRepository.updateOrInsertPlaylist(playlist);

        if (newOrder.size() < playlist.getPlaylistSongsListWithId().size()) {
            printLNGreen("Playlist reordered successfully with remaining songs added at the end!");
        } else {
            printLNGreen("Playlist reordered successfully!");
        }
    }

    private LinkedList<Integer> collectNewOrderFromUser(Playlist playlist, Scanner scanner) {
        LinkedList<Integer> newOrder = new LinkedList<>();
        int songIndex;

        printLNWhite("Enter the new order of songs (one by one). Press \"x\" to finish:");

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
                        printLNInfo("This song is already in the new order. Try again.");
                    }
                } else {
                    printInvalidInputTryAgain();
                }
            } catch (NumberFormatException e) {
                printInvalidInput();
                printInfo("Please enter a number or \"x\" to finish.");
            }
        }

        return newOrder;
    }

    private boolean isValidIndex(int index, Playlist playlist) {
        return index >= 0 && index < playlist.getPlaylistSongsListWithId().size();
    }

    private void completeWithRemainingSongs(Playlist playlist, List<Integer> newOrder) {
        List<Integer> allSongs = playlist.getPlaylistSongsListWithId();
        for (Integer songId : allSongs) {
            if (!newOrder.contains(songId)) {
                newOrder.add(songId);
            }
        }
    }
}
