package br.edu.ufcg.embedded.motofest.model;


public class User {
    private int idUser;
    private String idPlus;
    private String registrationId;
    private String name;
    private String email;
    private String urlPhoto;

    public User(int idUser, String idPlus, String registrationId, String name, String email, String urlPhoto) {
        this.idUser = idUser;
        this.idPlus = idPlus;
        this.registrationId = registrationId;
        this.name = name;
        this.email = email;
        this.urlPhoto = urlPhoto;
    }

    public int getId() {
        return idUser;
    }

    public void setId(int idUser) {
        this.idUser = idUser;
    }

    public String getIdPlus() {
        return idPlus;
    }

    public void setIdPlus(String idPlus) {
        this.idPlus = idPlus;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
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

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}
