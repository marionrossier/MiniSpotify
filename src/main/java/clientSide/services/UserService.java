package clientSide.services;

import common.entities.PlanEnum;
import common.entities.User;
import common.*;

import java.util.ArrayList;
import java.util.List;

import static clientSide.services.PrintHelper.*;

public class UserService {
    private final IUserRepository userLocalRepository;
    private final PasswordService passwordService;

    public UserService(ToolBoxService toolBoxService, PasswordService passwordService){
        this.userLocalRepository = toolBoxService.userLocalRepository;
        this.passwordService = passwordService;
    }

    public void addUser(String pseudonym, String email, String password, PlanEnum plan) {
        byte[] salt = passwordService.generateSalt();
        String hashedPassword = passwordService.hashPassword(password, salt);

        User existingUser = getUserByPseudonym(pseudonym);
        if (existingUser != null) {
            printLNInfo("The pseudonym \""+pseudonym+ "\" already exists.");
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
            printLNInfo("The pseudonym \""+pseudonym+ "\" already exists.");
        }
        else {
            User newUser = new User(id, pseudonym, email, hashedPassword, salt, plan, new ArrayList<>(), new ArrayList<>());
            userLocalRepository.saveUser(newUser);
        }
    }

    public boolean emailValidation(String email) {
        String emailRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        if (email != null && email.matches(emailRegex)) {
            return true;
        }
        return false;
    }

    public void addOnePlaylistToCurrentUser(int playlistId) {
        User user = userLocalRepository.getUserById(getCurrentUserId());
        List<Integer> playlists = user.getPlaylists();
        if (playlists == null) {
            playlists = new ArrayList<>();
            user.setPlaylists(playlists);
        }
        playlists.add(playlistId);
        userLocalRepository.saveUser(user);
        printLNGreen("Playlist has been added.");
    }

    public void addFriend(int friendId) {
        User user = userLocalRepository.getUserById(getCurrentUserId());
        List<Integer> friends = user.getFriends();
        if (!friends.contains(friendId)) {
            friends.add(friendId);
            userLocalRepository.saveUser(user);
            printLNGreen("Friend add to your friend list.");
        }
        else {
            printLNInfo("You're already friends !");
        }
    }

    public void deleteFriend(int friendId) {
        User user = userLocalRepository.getUserById(getCurrentUserId());
        List<Integer> friends = user.getFriends();
        friends.remove(Integer.valueOf(friendId));
        userLocalRepository.saveUser(user);
    }

    public void saveUser (User user){
        userLocalRepository.saveUser(user);
    }

    public int getCurrentUserId(){
        return Cookies.getInstance().getUserId();
    }

    public void resetCookie (){
        Cookies.resetCookies();
    }

    public int getUserIdByPseudo(String pseudo) {

        User searchedUser = getUserByPseudonym(pseudo);
        if (searchedUser == null) {
            return -1; // Return -1 if the pseudo was not found
        }
        return searchedUser.getUserId();
    }

    public User getUserByPseudonym(String pseudonym) {
        return userLocalRepository.getUserByPseudonym(pseudonym);
    }

    public List<Integer> getUsersByPseudonym(String pseudonym){
        List<Integer> userIds = new ArrayList<>();
        List<User> allUsers = userLocalRepository.getAllUsers();
        if (pseudonym == null || pseudonym.isEmpty()) {
            return userIds;
        }
        for (User user : allUsers) {
            if (user.getPseudonym() != null && user.getPseudonym().toLowerCase().contains(pseudonym.toLowerCase())) {
                userIds.add(user.getUserId());
            }
        }
        return userIds;
    }

    public User getUserById(int userId) {
        return userLocalRepository.getUserById(userId);
    }

    public User getUserByPseudonymLogin(String pseudonym) {
        return userLocalRepository.getUserByPseudonymLogin(pseudonym);
    }

    public void setCurrentFriendId (int friendId){
        Cookies.getInstance().setCurrentFriendId(friendId);
    }

    public int getCurrentFriendId (){
        return Cookies.getInstance().getCurrentFriendId();
    }
}