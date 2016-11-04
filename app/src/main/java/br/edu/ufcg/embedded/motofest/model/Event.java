package br.edu.ufcg.embedded.motofest.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.edu.ufcg.embedded.motofest.utils.AtracoesListItem;


public interface Event {

     String getNomeEvento();
     HashMap<String, List<AtracoesListItem>> getMapaEventosAtracoes();
     ArrayList<MarkerSettings> getMarkersSettings(String language);
    ArrayList<String> getTypeMarkers(ArrayList<MarkerSettings> markers);
     void setLanguage(String language);
     int getNotificacaoDia(String data);
     List<String> getAllNotification();

}
