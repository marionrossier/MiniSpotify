package clientSide.services;

import common.entities.PlanEnum;
import common.entities.User;
import common.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

import static clientSide.services.PrintHelper.*;

public class UserService {
    private final IUserRepository userRepository;
    private final PasswordService passwordService;

    public UserService(ToolBoxService toolBoxService, PasswordService passwordService){
        this.userRepository = toolBoxService.userRepository;
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
            saveUser(newUser);
            printLNGreen("Account created successfully !");
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
            saveUser(newUser);
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
        User user = userRepository.getUserById(getCurrentUserId());
        List<Integer> playlists = user.getPlaylists();
        if (playlists == null) {
            playlists = new ArrayList<>();
            user.setPlaylists(playlists);
        }
        playlists.add(playlistId);
        saveUser(user);
    }

    public void addFriend(int friendId) {
        User user = userRepository.getUserById(getCurrentUserId());
        List<Integer> friends = user.getFriends();
        if (!friends.contains(friendId)) {
            friends.add(friendId);
            saveUser(user);
            printLNGreen("Friend add to your friend list.");
        }
        else {
            printLNInfo("You're already friends !");
        }
    }

    public void deleteFriend(int friendId) {
        User user = userRepository.getUserById(getCurrentUserId());
        List<Integer> friends = user.getFriends();
        friends.remove(Integer.valueOf(friendId));
        saveUser(user);
    }

    public void saveUser (User user){
        userRepository.updateOrInsertUser(user);
    }

    public int getCurrentUserId(){
        return Cookies.getInstance().getUserId();
    }

    public void resetCookie (){
        Cookies.resetCookies();
    }

    public User getUserByPseudonym(String pseudonym) {
        return userRepository.getUserByPseudonym(pseudonym);
    }

    public List<Integer> getUsersByPseudonym(String pseudonym){
        List<Integer> userIds = new ArrayList<>();
        List<User> allUsers = userRepository.getAllUsers();
        int currentUserId = getCurrentUserId();
        if (pseudonym == null || pseudonym.isEmpty()) {
            return userIds;
        }
        for (User user : allUsers) {
            int userId = user.getUserId();
            if (user.getPseudonym() != null
                    && user.getPseudonym().toLowerCase().contains(pseudonym.toLowerCase())
                    && userId != 1
                    && userId != currentUserId) {
                userIds.add(userId);
            }
        }
        return userIds;
    }

    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    public void setCurrentFriendId (int friendId){
        Cookies.getInstance().setCurrentFriendId(friendId);
    }

    public int getCurrentFriendId (){
        return Cookies.getInstance().getCurrentFriendId();
    }
}