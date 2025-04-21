package services.Cookies_SingeltonPattern;

public class CookiePlaylist {
    private static CookiePlaylist instance;

    private final int id;

    private CookiePlaylist(int id ) {
        this.id = id;
    }

    public static CookiePlaylist setInstance(int id) {
        if (instance == null) {
            instance = new CookiePlaylist(id);
        }
        return instance;
    }

    public static CookiePlaylist reset(){
        instance = null;
        return instance;
    }

    public static CookiePlaylist getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized.");
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Cookies{" +
                "ID=" + id +
                '}';
    }
}
