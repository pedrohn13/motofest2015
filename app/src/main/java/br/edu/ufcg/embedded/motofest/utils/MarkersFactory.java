package br.edu.ufcg.embedded.motofest.utils;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MarkersFactory {
    private final ArrayList<MarkerOptions> markers;

    public MarkersFactory(Context context) {
        markers = new ArrayList<>();
    }


    public void createMarker(double lat, double lng, String title, String snippet, float hue) {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(title).snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(hue));

        if (!contains(marker)) {
            markers.add(marker);
        }

    }

    public ArrayList<MarkerOptions> getMarkers() {
        return markers;
    }

    public void clearMarkers() {
        markers.clear();
    }

    public boolean isEmpty() {
        return markers.isEmpty();
    }

    private boolean contains(MarkerOptions markerOptions) {
        for (MarkerOptions marker: getMarkers()) {
            if (marker.equals(markerOptions)) {
                return true;
            }
        }
        return false;
    }

}
