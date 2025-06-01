package clientSide.services;

public class Cookies {
    private static Cookies instance;

    private final int userId;
    private final String userPseudonym;
    private final String userPassword;
    private int currentFriendId;
    private int currentFriendPlaylistId;
    private int currentPlaylistId;
    private int currentSongId;

    private Cookies(int userId, String userPseudonym, String userPassword) {
        this.userId = userId;
        this.userPseudonym = userPseudonym;
        this.userPassword = userPassword;
        this.currentPlaylistId = 914903479; //AllSongs
        this.currentSongId = 1108071776; //Amy Winehouse - Rehab
        this.currentFriendId = 0;
        this.currentFriendPlaylistId = 0;
    }

    public static void initializeInstance(int userId, String userPseudonym, String userPassword) {
        if (instance == null) {
            instance = new Cookies(userId, userPseudonym,userPassword);
        }
    }

    public static void setCurrentPlaylistId(int currentPlaylistId) {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized. Please set the user first.");
        }
        instance.currentPlaylistId = currentPlaylistId;
    }

    public static void setCurrentSongId(int currentSongId) {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized. Please set the user first.");
        }
        instance.currentSongId = currentSongId;
    }

    public static void resetCookies() {
        if (instance != null) {
            instance = null;
        }
    }

    public static Cookies getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized.");
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserPseudonym() {
        return userPseudonym;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public int getCurrentPlaylistId() {
        return currentPlaylistId;
    }

    public int getCurrentSongId() {
        return currentSongId;
    }

    @Override
    public String toString() {
        return "Cookies{" +
                "userId=" + userId +
                ", currentPlaylistId=" + currentPlaylistId +
                '}';
    }

    public int getCurrentFriendId() {
        return currentFriendId;
    }

    public void setCurrentFriendId(int friendId){
        this.currentFriendId = friendId;
    }

    public int getCurrentFriendPlaylistId() {
        return currentFriendPlaylistId;
    }

    public void setCurrentFriendPlaylistId(int currentFriendPlaylistId) {
        this.currentFriendPlaylistId = currentFriendPlaylistId;
    }
}