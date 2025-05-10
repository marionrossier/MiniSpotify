package services;

import data.entities.Playlist;
import data.entities.PlaylistEnum;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;

import java.util.*;

public class PlaylistServices {

    //TODO: optimiser ou séparer cette classe qui devient trop grande
    Scanner scanner = new Scanner(System.in);
    private final UserRepository userRepository;
    final PlaylistRepository playlistRepository;
    private final UserService userService;

    public PlaylistServices (PlaylistRepository playlistRepository, UserRepository userRepository){
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.userService = new UserService(userRepository);
    }

    public PlaylistServices (PlaylistRepository playlistRepository){
        this.playlistRepository = playlistRepository;
        this.userRepository = new UserRepository();
        this.userService = new UserService(userRepository);
    }

    public void removePlaylistFromUser (int playlistId){
        User user = userRepository.getUserById(userService.getCurrentUserId());
        List<Integer> actualPlaylists = user.getPlaylists();
        int playlistIndex = actualPlaylists.indexOf(playlistId);

        actualPlaylists.remove(playlistIndex);
    }

    public void deletePlaylist(int playlistId) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        if (playlist.getStatus().equals(PlaylistEnum.PRIVATE)){
            playlistRepository.deletePlaylistById(playlistId);
            System.out.println("Playlist deleted !");
        }
        else {
            if (playlist.getName().equals("AllSongs")){
                System.err.println("You cannot delete the AllSongs playlist.");
            }
            removePlaylistFromUser(playlistId);
            System.out.println("Playlist removed from your list.");
        }
    }

    public int takeAndValidateInputSongChoice(int playlistId) {
        Playlist playlist = getPlaylistById(playlistId);
        int chosenSong = -1;

        while (true) {
            String input = this.scanner.nextLine();

            if (input.equals("0")) {
                return 0;
            }

            try {
                int inputNumber = Integer.parseInt(input);

                if (inputNumber < 1 || inputNumber > playlist.getSize()) {
                    System.err.println("Invalid Playlist number.");
                    System.out.println("Try again or press \"0\" to go back : ");
                } else {
                    chosenSong = inputNumber-1;
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input, please enter a number : ");
            }
        }

        return chosenSong;
    }

    public int takeAndValidationInputPlaylistChoice() {
        User currentUser = userRepository.getUserById(userService.getCurrentUserId());

        int chosenPlaylist = -1;

        while (true) {
            String input = this.scanner.nextLine();

            if (input.equals("0")) {
                return 0;
            }

            try {
                int inputNumber = Integer.parseInt(input);

                if (inputNumber < 1 || inputNumber > currentUser.getPlaylists().size()) {
                    System.err.println("Invalid Playlist number.");
                    System.out.println("Try again or press \"0\" to go back : ");
                } else {
                    chosenPlaylist = currentUser.getPlaylists().get(inputNumber - 1);
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input, please enter a number : ");
            }
        }

        return chosenPlaylist;
    }

    //TODO : Utilisé que dans les tests...
    public void addSong(int currentPlaylistId, int currentSongId) {
        Playlist playlist = this.playlistRepository.getPlaylistById(currentPlaylistId);
        playlist.getPlaylistSongsListWithId().add(currentSongId);
        int playlistDuration = playlist.getDurationSeconds();
        int playlistSize = playlist.getSize();
        playlist.setPlaylistInformation(playlistDuration, playlistSize);

        this.playlistRepository.savePlaylist(playlist);
    }

    public void addSongToPlaylistFromTemporaryPlaylist(int temporaryPlaylistId, int finalPlaylistId) {
        Playlist temporaryPlaylist = playlistRepository.getPlaylistById(temporaryPlaylistId);
        Playlist targetPlaylist = playlistRepository.getPlaylistById(finalPlaylistId);

        if (targetPlaylist != null && temporaryPlaylist != null) {
            for (Integer songId : temporaryPlaylist.getPlaylistSongsListWithId()) {
                if (!targetPlaylist.getPlaylistSongsListWithId().contains(songId)) {
                    targetPlaylist.getPlaylistSongsListWithId().add(songId);
                }
            }
            playlistRepository.savePlaylist(targetPlaylist);
        } else {
            System.err.println("Target playlist or temporary playlist not found.");
        }
    }

    public void renamePlayList(int playlistId, String newName) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        playlist.setName(newName);

        playlistRepository.savePlaylist(playlist);
        System.out.println("Playlist renamed to " + newName + " !");
    }

    public void deleteSongFromPlaylist(int playlistId, int songIndex) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        playlist.getPlaylistSongsListWithId().remove(songIndex);
        playlistRepository.savePlaylist(playlist);
    }

    public void deleteTemporaryPlaylist() {
        Playlist temporaryPlaylist = playlistRepository.getPlaylistById(getTemporaryPlaylistId());
        if (temporaryPlaylist != null) {
            int playlistId = temporaryPlaylist.getPlaylistId();
            playlistRepository.deletePlaylistById(playlistId);
        }
    }

    public void createNewPlaylist (String playlistName, PlaylistEnum status){
        createPlaylistWithTemporaryPlaylist(playlistName, status);
        int playlistId = getPlaylistByName(playlistName).getPlaylistId();
        setCurrentPlaylistId(playlistId);
        System.out.println();
        System.out.println("Playlist created successfully !");
    }

    public void createTemporaryPlaylist(LinkedList<Integer> chosenSongs, PlaylistEnum status) {
        int currentUserId = userService.getCurrentUserId();
        Playlist temporaryPlaylist = playlistRepository.getTemporaryPlaylistOfCurrentUser(userService);

        if (temporaryPlaylist == null) {
            temporaryPlaylist = new Playlist("temporaryPlaylist", PlaylistEnum.PRIVATE);
            playlistRepository.savePlaylist(temporaryPlaylist);
        }
        temporaryPlaylist.setListSongsId(chosenSongs);

        int playlistDuration = temporaryPlaylist.getDurationSeconds();
        int playlistSize = temporaryPlaylist.getSize();

        temporaryPlaylist.setPlaylistInformation(playlistDuration, playlistSize);
        temporaryPlaylist.setOwnerId(currentUserId);
        temporaryPlaylist.setStatus(status);

        playlistRepository.savePlaylist(temporaryPlaylist);
    }

    public void createPlaylistWithTemporaryPlaylist(String playlistName, PlaylistEnum status) {

        Playlist newPlaylist = new Playlist(playlistName, PlaylistEnum.PRIVATE);

        Playlist temporaryPlaylist = playlistRepository.getPlaylistByName("temporaryPlaylist");

        newPlaylist.setListSongsId(temporaryPlaylist.getPlaylistSongsListWithId());
        int playlistDuration = newPlaylist.getDurationSeconds();
        int playlistSize = newPlaylist.getSize();
        newPlaylist.setPlaylistInformation(playlistDuration, playlistSize);
        newPlaylist.setOwnerId(userService.getCurrentUserId());
        newPlaylist.setStatus(status);

        playlistRepository.savePlaylist(newPlaylist);

        userService.addOnePlaylist(newPlaylist.getPlaylistId());

        this.deleteTemporaryPlaylist();
    }
    
    public int getCurrentPlaylistId (){
        return Cookies_SingletonPattern.getInstance().getCurrentPlaylistId();
    }

    public PlaylistEnum getPlaylistStatus (){
        return playlistRepository.getPlaylistStatus(getCurrentPlaylistId());
    }

    public int getTemporaryPlaylistId() {
        Playlist playlist = playlistRepository.getPlaylistByName("temporaryPlaylist");
        if (playlist == null) {
            return new Playlist("temporaryPlaylist", PlaylistEnum.PRIVATE).getPlaylistId();
        }
        return playlist.getPlaylistId();
    }

    public void setCurrentPlaylistId (int playlistId){
        Cookies_SingletonPattern.setCurrentPlaylistId(playlistId);
    }

    public int getAllSongsPlaylistId (){
        return playlistRepository.getPlaylistByName("AllSongs").getPlaylistId();
    }

    public void createAllSongPlaylist (User user){
        int allSongsPlaylistId = getAllSongsPlaylistId();
        List<Integer> playlists = user.getPlaylists();

        if (!playlists.contains(allSongsPlaylistId)) {
            playlists.add(0, allSongsPlaylistId);
        }
        userRepository.saveUser(user);
    }

    public void playlistPageRouter(PageService pageService, SongService songService) {
        int chosenPlaylist = takeAndValidationInputPlaylistChoice();

        if (chosenPlaylist == 0) {
            pageService.homePage.displayAllPage();
            return;
        }
        setCurrentPlaylistId(chosenPlaylist);
        songService.setCurrentSongId(playlistRepository.getPlaylistById(chosenPlaylist).getPlaylistSongsListWithId().getFirst());

        if (isCurrentUserOwnerOfPlaylist(chosenPlaylist)){
            pageService.playlistPageOpen.displayAllPage();
        }
        else {
            pageService.playlistPageShared.displayAllPage();
        }
    }

    public boolean isCurrentUserOwnerOfPlaylist(int playlistId) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        if (playlist == null) {
            return false;
        }
        int currentUserId = userService.getCurrentUserId();
        return playlist.getOwnerId() == currentUserId;
    }

    public Playlist getPlaylistByName(String name) {
        return playlistRepository.getPlaylistByName(name);
    }

    public Playlist getPlaylistById(int id) {
        return playlistRepository.getPlaylistById(id);
    }

    public boolean verifyPlaylistName(String playlistName, User user) {
        List<Integer> userPlaylistsIds = user.getPlaylists();

        for (Integer playlistId : userPlaylistsIds) {
            Playlist playlist = playlistRepository.getPlaylistById(playlistId);
            if (playlist != null && playlist.getName().equalsIgnoreCase(playlistName)) {
                return false;
            }
        }
        return true;
    }

    public List<Playlist> getPublicPlaylists() {
        List<Playlist> allPlaylists = playlistRepository.getAllPlaylists();
        List<Playlist> publicPlaylist = new ArrayList<>();

        for (Playlist playlist : allPlaylists){
            if (playlist.getStatus().equals(PlaylistEnum.PUBLIC)){
                publicPlaylist.add(playlist);
            }
        }
        return  publicPlaylist;
    }

    public LinkedList<Playlist> getSharedPlaylist(User followedUser) {
        throw new UnsupportedOperationException("Not implemented yet");
        /*TODO*/
    }

    public LinkedList<Integer> chooseFoundedPlaylist(List<Playlist> playlist, PageService pageService){
        LinkedList<Integer> selectedPlaylistIndex = new LinkedList<>();
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
                int playlistIndex = Integer.parseInt(input) - 1;
                if (playlistIndex >= 0 && playlistIndex < playlist.size()) {
                    selectedPlaylistIndex.add(playlistIndex);
                } else {
                    System.err.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number or \"x\" to exit.");
            }
        }
        return selectedPlaylistIndex;
    }


    //TODO : mettre dans une classe différente
    public void reorderSongsInPlaylist(int playlistId, PageService pageService) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        LinkedList<Integer> newOrder = collectNewOrderFromUser(playlist);

        completeWithRemainingSongs(playlist, newOrder);
        playlist.setListSongsId(newOrder);
        playlistRepository.savePlaylist(playlist);

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
