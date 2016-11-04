package br.edu.ufcg.embedded.motofest.model;


public class Email {
    private String name;
    private String email;

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

    public String getAvaliation() {
        return avaliation;
    }

    public void setAvaliation(String avaliation) {
        this.avaliation = avaliation;
    }

    private String avaliation;

    public Email(String name, String email, String avaliation) {
        this.name = name;
        this.email = email;
        this.avaliation = avaliation;
    }
}
