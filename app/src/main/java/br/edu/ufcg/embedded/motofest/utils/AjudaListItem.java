package br.edu.ufcg.embedded.motofest.utils;

import java.io.Serializable;


public class AjudaListItem implements Serializable {

    private final String label;

    public AjudaListItem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


}
