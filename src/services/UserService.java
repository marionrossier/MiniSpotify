package services;

import data.entities.PlanEnum;
import data.entities.User;
import data.jsons.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordService = new PasswordService(userRepository);
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
            userRepository.saveUser(newUser);
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
            userRepository.saveUser(newUser);
        }
    }

    public int getCurrentUserId(){
        return Cookies_SingletonPattern.getInstance().getUserId();
    }

    public void resetCookie (){
        Cookies_SingletonPattern.resetCookies();
    }

    public void addOnePlaylist(int playlistId) {
        List<Integer> playlists = userRepository.getUserById(getCurrentUserId()).getPlaylists();
        if (playlists == null) {
            playlists = new ArrayList<>();
            userRepository.getUserById(getCurrentUserId()).setPlaylists(playlists);
        }
        userRepository.addPlaylistToUser(getCurrentUserId(),playlistId);
    }

    public void followFriend() {/*TODO*/}
    public void unfollowFriend() {/*TODO*/}

    public User getUserByPseudonym(String pseudonym) {
        return userRepository.getUserByPseudonym(pseudonym);
    }

    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    public boolean emailValidation(String email) {
        String emailRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        if (email != null && email.matches(emailRegex)) {
            return true;
        }
        return false;
    }
}