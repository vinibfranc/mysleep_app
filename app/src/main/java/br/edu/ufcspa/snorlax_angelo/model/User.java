package br.edu.ufcspa.snorlax_angelo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by icaromsc on 18/01/2017.
 *
 * Classe modelo de Usuario do app
 *
 */

public class User {
    private Integer idUser;
    private String id_user_google;
    private String id_user_facebook;
    private String name;
    private String email;
    private String picture;
    private String smartphoneInfo;

    public User() {}

    public User(Integer idUser, String id_user_google, String id_user_facebook, String name, String email, String picture) {
        this.idUser = idUser;
        this.id_user_google = id_user_google;
        this.id_user_facebook = id_user_facebook;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public String getSmartphoneInfo() {
        return smartphoneInfo;
    }

    public void setSmartphoneInfo(String smartphoneInfo) {
        this.smartphoneInfo = smartphoneInfo;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getId_user_google() {
        return id_user_google;
    }

    public void setId_user_google(String id_user_google) {
        this.id_user_google = id_user_google;
    }

    public String getId_user_facebook() {
        return id_user_facebook;
    }

    public void setId_user_facebook(String id_user_facebook) {
        this.id_user_facebook = id_user_facebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", id_user_google='" + id_user_google + '\'' +
                ", id_user_facebook='" + id_user_facebook + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }


    public JSONObject toJson(){
        JSONObject j = new JSONObject();
        try {
            j.put("id_user_facebook",getId_user_facebook());
            j.put("id_user_google",getId_user_google());
            j.put("name",getName());
            j.put("email",getEmail());
            j.put("smartphone_info",smartphoneInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }
}

