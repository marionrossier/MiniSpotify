package services;

import data.entities.PlanEnum;
import data.entities.User;
import data.jsons.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final PasswordService passwordService = new PasswordService();

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public int getUserIdByPseudo(String pseudo) {

        User searchedUser = userRepository.getUserByPseudonym(pseudo);
        if (searchedUser == null) {
            return -1; // Return -1 if the pseudo was not found
        }
        return searchedUser.getUserId();
    }

    public void addUser(String pseudonym, String email, String password, PlanEnum plan) {
        byte[] salt = passwordService.generateSalt();
        String hashedPassword = passwordService.hashPassword(password, salt);

        User existingUser = userRepository.getUserByPseudonym(pseudonym);
        if (existingUser != null) {
            System.err.println("The pseudonym \""+pseudonym+ "\" already exists.");
        }
        else {
            User newUser = new User(pseudonym, email, hashedPassword, salt, plan, new ArrayList<>(), new ArrayList<>());
            userRepository.saveUser(newUser);
        }
    }

    public boolean verifyUserAuthentification(String pseudonym, String password) {

        User searchedUser = userRepository.getUserByPseudonym(pseudonym);

        if (searchedUser == null) {
            System.err.println("The user does not exist.");
            return false;
        }

        String givenHashedPassword = passwordService.hashPassword(password, searchedUser.getSalt());

        if (givenHashedPassword.equals(searchedUser.getPassword())) {
            return true;
        } else {
            System.err.println("The password is incorrect.");
            return false;
        }
    }
    public int getCookieUserId (){
        return Cookies_SingletonPattern.getInstance().getUserId();
    }

    public void addOnePlaylist(int playlistId) {
        List<Integer> playlists = userRepository.getUserById(getCookieUserId()).getPlaylists();
        if (playlists == null) {
            playlists = new ArrayList<>();
            userRepository.getUserById(getCookieUserId()).setPlaylists(playlists);
        }
        userRepository.addPlaylistToUser(getCookieUserId(),playlistId);
    }

    public void followFriend() {/*TODO*/}
    public void unfollowFriend() {/*TODO*/}
}