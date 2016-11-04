package br.edu.ufcg.embedded.motofest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import br.edu.ufcg.embedded.motofest.model.Event;
import br.edu.ufcg.embedded.motofest.model.MarkerSettings;
import br.edu.ufcg.embedded.motofest.utils.AtracoesListItem;


public final class Controller {

    private static Controller instance;
    private final Event event;

    private Controller(Event event) {
        this.event = event;
    }


    public static Controller getInstance(Event event) {
        if (instance == null) {
            instance = new Controller(event);
        }
        return instance;
    }

    public void setLanguage(Locale locale) {
        event.setLanguage(String.valueOf(locale));
    }

    public HashMap<String, List<AtracoesListItem>> getMapaEventosAtracoes() {
        return event.getMapaEventosAtracoes();
    }

    public String getNomeEvento() {
        return event.getNomeEvento();
    }

    public ArrayList<MarkerSettings> getMarkersSettings(Locale locale) {
        setLanguage(locale);
        return event.getMarkersSettings(String.valueOf(locale));
    }

    public ArrayList<String> getTypeMarkers(Locale locale) {
        setLanguage(locale);
        return event.getTypeMarkers(getMarkersSettings(locale));
    }

    public int getNotificacaoDia(String data) {
        return event.getNotificacaoDia(data);
    }

    public List<String> getAllNotification() {
        return event.getAllNotification();
    }
}
