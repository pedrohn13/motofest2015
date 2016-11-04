package br.edu.ufcg.embedded.motofest.utils;

import java.io.Serializable;

/**
 * Created by Pedro
 */
public class AtracoesListItem implements Serializable {

    private final String label;
    private String data;
    private String schedule;
    private final boolean isBanda;

    public AtracoesListItem(String label) {
        this.label = label;
        isBanda = false;
    }

    public AtracoesListItem(String label, String schedule) {
        this.label = label;
        this.schedule = schedule;
        isBanda = false;
    }

    public AtracoesListItem(String label, String schedule, boolean isBanda) {
        this.label = label;
        this.schedule = schedule;
        this.isBanda = isBanda;
    }

    public String getLabel() {
        return label;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;

    }

    public boolean isBand() {
        return isBanda;
    }

    public String getSchedule() {
        return schedule;
    }


}
