package br.edu.ufcg.embedded.motofest.utils;



import java.io.Serializable;

public class PartnersListItem implements Serializable {

    private final String subtitle;
    private final String label;
    private String data;
    private int image;
    private int imagelarge;

    public PartnersListItem(String label, String subtitle) {
        this.label = label;
        this.subtitle = subtitle;
    }

    public String getLabel() {
        return label;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getData() {
        return data;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImagelarge() {
        return imagelarge;
    }

    public void setImagelarge(int imagelarge) {
        this.imagelarge = imagelarge;
    }
}
