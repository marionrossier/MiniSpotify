package clientSide.services;

import serverSide.entities.PlanEnum;
import serverSide.entities.User;
import serverSide.repositories.UserLocalRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final UserLocalRepository userLocalRepository;
    private final PasswordService passwordService;

    public UserService(ServiceToolBox serviceToolBox, PasswordService passwordService){
        this.userLocalRepository = serviceToolBox.userLocalRepository;
        this.passwordService = passwordService;
    }

    public int getUserIdByPseudo(String pseudo) {

        User searchedUser = getUserByPseudonym(pseudo);
        if (searchedUser == null) {
            return -1; // Return -1 if the pseudo was not found
        }
        return searchedUser.getUserId();
    }

    public void addUser(String pseudonym, String email, String password, PlanEnum plan) {
        byte[] salt = passwordService.generateSalt();
        String hashedPassword = passwordService.hashPassword(password, salt);

        User existingUser = getUserByPseudonym(pseudonym);
        if (existingUser != null) {
            System.err.println("The pseudonym \""+pseudonym+ "\" already exists.");
        }
        else {
            User newUser = new User(pseudonym, email, hashedPassword, salt, plan, new ArrayList<>(), new ArrayList<>());
            userLocalRepository.saveUser(newUser);
        }
    }

    public void addUser(int id, String pseudonym, String email, String password, PlanEnum plan) {
        byte[] salt = passwordService.generateSalt();
        String hashedPassword = passwordService.hashPassword(password, salt);

        User existingUser = getUserByPseudonym(pseudonym);
        if (existingUser != null) {
            System.err.println("The pseudonym \""+pseudonym+ "\" already exists.");
        }
        else {
            User newUser = new User(id, pseudonym, email, hashedPassword, salt, plan, new ArrayList<>(), new ArrayList<>());
            userLocalRepository.saveUser(newUser);
        }
    }

    public int getCurrentUserId(){
        return Cookies_SingletonPattern.getInstance().getUserId();
    }

    public void saveUser (User user){
        userLocalRepository.saveUser(user);
    }

    public void resetCookie (){
        Cookies_SingletonPattern.resetCookies();
    }

    public void addOnePlaylist(int playlistId) {
        List<Integer> playlists = userLocalRepository.getUserById(getCurrentUserId()).getPlaylists();
        if (playlists == null) {
            playlists = new ArrayList<>();
            userLocalRepository.getUserById(getCurrentUserId()).setPlaylists(playlists);
        }
        userLocalRepository.addPlaylistToUser(getCurrentUserId(),playlistId);
    }

    public void followFriend() {/*TODO*/}
    public void unfollowFriend() {/*TODO*/}

    public User getUserByPseudonym(String pseudonym) {
        return userLocalRepository.getUserByPseudonym(pseudonym);
    }

    public User getUserById(int userId) {
        return userLocalRepository.getUserById(userId);
    }

    public boolean emailValidation(String email) {
        String emailRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        if (email != null && email.matches(emailRegex)) {
            return true;
        }
        return false;
    }
}