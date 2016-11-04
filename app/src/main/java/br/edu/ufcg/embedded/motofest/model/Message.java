package br.edu.ufcg.embedded.motofest.model;


public class Message {

    private int idMessage;
    private String idUser;
    private String nameUser;
    private String urlUser;
    private String content;
    private String dateSend;
    private int visualized;

    public Message(int idMessage, String idUser, String nameUser, String urlUser, String content, String dateSend, int visualized) {
        this.idMessage = idMessage;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.urlUser = urlUser;
        this.content = content;
        this.dateSend = dateSend;
        this.visualized = visualized;
    }

    public int getId() {
        return idMessage;
    }

    public void setId(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getUrlUser() {
        return urlUser;
    }

    public void setUrlUser(String urlUser) {
        this.urlUser = urlUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateSend() {
        return dateSend;
    }

    public void setDateSend(String dateSend) {
        this.dateSend = dateSend;
    }

    public int getVisualized() {
        return visualized;
    }

    public void setVisualized(int visualized) {
        this.visualized = visualized;
    }


}
