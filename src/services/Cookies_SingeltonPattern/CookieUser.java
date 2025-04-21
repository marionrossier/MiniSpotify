package services.Cookies_SingeltonPattern;

public class CookieUser {
    private static CookieUser instance;

    private final int id;

    private CookieUser(int id ) {
        this.id = id;
    }

    public static CookieUser setInstance(int id) {
        if (instance == null) {
            instance = new CookieUser(id);
        }
        return instance;
    }

    public static CookieUser reset(){
        instance = null;
        return instance;
    }

    public static CookieUser getInstance() {
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
