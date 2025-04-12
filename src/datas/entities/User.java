package datas.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // Inclut uniquement les champs non nuls
public class User {
    private int userGuId;
    private String pseudonym;
    private String email;
    private String password;
    private byte[] salt;
    private PlanEnum planEnum;
    private List<Integer> playlists;

    // Getters et setters publics
    public int getUserGuId() {
        return userGuId;
    }

    public void setUserGuId(int userGuId) {
        this.userGuId = userGuId;
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

}