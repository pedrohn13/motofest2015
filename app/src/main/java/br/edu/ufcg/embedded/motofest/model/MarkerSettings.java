package br.edu.ufcg.embedded.motofest.model;


public class MarkerSettings {
    private double latitude;
    private double longitude;
    private String title;
    private String snippet;
    private float hue;
    private String type;

    public MarkerSettings(String type, double lat, double lng, String title, String snippet, float hue) {
        this.type = type;
        this.latitude = lat;
        this.longitude = lng;
        this.title = title;
        this.snippet = snippet;
        this.hue = hue;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public float getHue() {
        return hue;
    }

    public String getType() {
        return type;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTitleId(String titleId) {
        this.title = titleId;
    }

    public void setSnippetId(String snippet) {
        this.snippet = snippet;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public void setType(String type) {
        this.type = type;
    }
}
