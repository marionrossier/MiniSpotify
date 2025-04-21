package data.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import services.TransverseService;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // Inclut uniquement les champs non nuls
public class User {
    private int userId;
    private String pseudonym;
    private String email;
    private String password;
    private byte[] salt;
    private PlanEnum planEnum;
    private List<Integer> playlists;
    private List<Integer> friends;
    private final TransverseService transverseService = new TransverseService();

    public User() {
    }

    public User(String pseudonym, String email, String password, PlanEnum planEnum) {
        this.pseudonym = pseudonym;
        this.email = email;
        this.password = password;
        this.planEnum = planEnum;
        this.userId = transverseService.setUniqueId();
    }

    public User(String pseudonym, String email, String password, byte[] salt,
                PlanEnum planEnum, List<Integer> playlists, List<Integer> friends) {
        this.pseudonym = pseudonym;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.planEnum = planEnum;
        this.userId = transverseService.setUniqueId();
        this.playlists = playlists;
        this.friends = friends;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalt() { // Getter for salt
        return salt;
    }

    public void setSalt(byte[] salt) { // Setter for salt
        this.salt = salt;
    }

    public PlanEnum getPlanEnum() {
        return planEnum;
    }

    public void setPlanEnum(PlanEnum planEnum) {
        this.planEnum = planEnum;
    }

    public List<Integer> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Integer> playlists) {
        this.playlists = playlists;
    }

    public List<Integer> getFriends() {
        return friends;
    }

    public void setFriends(List<Integer> friends) {
        this.friends = friends;
    }

    public void followFriend() {/*TODO*/}
    public void unfollowFriend() {/*TODO*/}

}