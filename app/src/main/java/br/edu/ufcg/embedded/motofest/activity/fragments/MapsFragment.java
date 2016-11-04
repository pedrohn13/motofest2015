package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.model.MarkerSettings;
import br.edu.ufcg.embedded.motofest.utils.MarkersFactory;


public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final double LATITUDE_PP = -7.2236957;
    private static final double LONGITUDE_PP = -35.8878092;
    private static final double LATITUDE_NORTHEAST_IMAGE = -7.2221133;
    private static final double LONGITUDE_NORTHEAST_IMAGE = -35.8857042;
    private static final double LATITUDE_SOUTHWEST_IMAGE = -7.2259440;
    private static final double LONGITUDE_SOUTHWEST_IMAGE = -35.889090;
    private static final float ZOOM_SCALE = 17.5f;
    private static final float TRANSPARENCY_OVERLAY = 0.2f;
    private static Context mContext;
    private MarkersFactory markersFactory;
    private SupportMapFragment mMapFragment;
    private LatLng ppLocation;
    private boolean isMarkerSelected;
    private boolean myLocationWasClicked;
    private View rootView;
    private GoogleMap mMap;
    private LatLng myLocation;
    private Location mLocation;
    private final GoogleMap.OnMyLocationChangeListener mLocationListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mLocation = location;
            if (myLocationWasClicked) {
                myLocationWasClicked = false;
                moveToMyLocation();
            }
        }
    };
    private Activity parentActivity;
    private ArrayList<Marker> markers;
    private ArrayList<String> markersTypes;
    private boolean goingToHome;
    private Bundle saved;
    private boolean markerDescriptionVisible;
    private Activity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapFragment = SupportMapFragment.newInstance();
        mMap = mMapFragment.getMap();
        ppLocation = new LatLng(LATITUDE_PP, LONGITUDE_PP);
        markers = new ArrayList<>();
        markersTypes = new ArrayList<>();
        mContext = getActivity();
        parentActivity = getActivity();
        markersFactory = new MarkersFactory(mContext);
        saved = savedInstanceState;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        showEneableMessageIfNeeded();
        setUpMapIfNeeded();

        final LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        rootView.findViewById(R.id.action_b).setOnClickListener(new View.OnClickListener() {

                                                                    @Override
                                                                    public void onClick(View view) {

                                                                        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) { //if gps is disabled
                                                                            myLocationWasClicked = true;
                                                                            displayPromptForEnablingGPS();
                                                                            // startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                                                        } else if (myLocation == null) {
                                                                            myLocationWasClicked = true;
                                                                            Toast.makeText(getActivity(), getString(R.string.carregando_localizacao), Toast.LENGTH_LONG).show();
                                                                            //loadMyLocation();
                                                                        } else {
                                                                            moveToMyLocation();

                                                                        }

                                                                    }
                                                                }

        );
        rootView.findViewById(R.id.action_c).

                setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ppLocation, ZOOM_SCALE);
                                           mMap.animateCamera(cameraUpdate);
                                       }
                                   }


                );

        return rootView;
    }


    public void showEneableMessageIfNeeded() {
        if (!isNetworkAvailable()) {
            displayPromptForEnablingInternet();
        }
    }
    private void moveToMyLocation() {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myLocation, ZOOM_SCALE);
        mMap.animateCamera(cameraUpdate);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMapFragment = SupportMapFragment.newInstance();
            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.map_container, mMapFragment);
            fragmentTransaction.commit();
            mMapFragment.getMapAsync(this);

        }
    }

    public ArrayList<Marker> getMarkers() {
        return markers;
    }

    private void setUpMap() {

        MapsInitializer.initialize(mContext);
        GroundOverlayOptions groundOverlayOptions = createGroundOverlay();
        try {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.map_container, mMapFragment).commit();
        } catch (IllegalStateException excep) {
            excep.printStackTrace();
        }

        mMap.setMyLocationEnabled(true);

        //Move camera para o parque do povo
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ppLocation, ZOOM_SCALE);
        mMap.moveCamera(cameraUpdate);


        //Cria GroundOverlay
        mMap.addGroundOverlay(groundOverlayOptions);

        mMap.setOnMyLocationChangeListener(mLocationListener);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.setOnMarkerClickListener(this);

        View parteTotalView = rootView.findViewById(R.id.tela_total);
        parteTotalView.setVisibility(View.INVISIBLE);
        View parte1View = rootView.findViewById(R.id.tela_parte1);
        disable(parte1View);
        View parte2View = rootView.findViewById(R.id.tela_parte2);
        disable(parte2View);
        View parte3View = rootView.findViewById(R.id.tela_parte3);
        disable(parte3View);
        View parte4View = rootView.findViewById(R.id.tela_parte4);
        disable(parte4View);
        View parte5View = rootView.findViewById(R.id.tela_parte5);
        disable(parte5View);
        View parte6View = rootView.findViewById(R.id.tela_parte6);
        disable(parte6View);
        View parte7View = rootView.findViewById(R.id.tela_parte7);
        disable(parte7View);


    }

    private GroundOverlayOptions createGroundOverlay() {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.layout);
        LatLng northEast = new LatLng(LATITUDE_NORTHEAST_IMAGE, LONGITUDE_NORTHEAST_IMAGE);
        LatLng southWest = new LatLng(LATITUDE_SOUTHWEST_IMAGE, LONGITUDE_SOUTHWEST_IMAGE);
        LatLngBounds latLngBounds = new LatLngBounds(southWest, northEast);
        GroundOverlayOptions groundOverlayOptionsAux = new GroundOverlayOptions();
        groundOverlayOptionsAux.positionFromBounds(latLngBounds);
        groundOverlayOptionsAux.image(bitmapDescriptor);
        groundOverlayOptionsAux.transparency(TRANSPARENCY_OVERLAY);
        return groundOverlayOptionsAux;
    }

    private void disable(View parte1View) {
        final View parteTotalView = rootView.findViewById(R.id.tela_total);
        final FloatingActionsMenu floatingActionsMenu = (FloatingActionsMenu) rootView.findViewById(R.id.multiple_actions);


        parte1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parteTotalView.getVisibility() == View.VISIBLE) {
                    parteTotalView.setVisibility(View.INVISIBLE);
                    floatingActionsMenu.setVisibility(View.VISIBLE);

                }
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            setUpMap();
            if (saved != null) {
                restoreMarkers(saved);
            }
        }
    }


    private void preencheCaixa(final Marker markerAux) {
        TextView tvNome = ((TextView) rootView.findViewById(R.id.nomeDialog));
        tvNome.setText(markerAux.getTitle());
        TextView tvDescricao = ((TextView) rootView.findViewById(R.id.nomeDescription));
        tvDescricao.setText(markerAux.getSnippet());
        ImageView ivRota = ((ImageView) rootView.findViewById(R.id.iv_rota));
        TextView ivRotaName = ((TextView) rootView.findViewById(R.id.iv_rota_name));

        View.OnClickListener onTracarClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracar(markerAux);
            }
        };

        ivRota.setOnClickListener(onTracarClick);
        ivRotaName.setOnClickListener(onTracarClick);
    }

    private void tracar(final Marker markerAux) {
        final LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) { //if gps is disabled
            displayPromptForEnablingGPS();
        } else {
            new TracaRota().execute(markerAux);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public boolean isGoingToHome() {
        return goingToHome;
    }

    private void buildMarkers(ArrayList<String> types) {
        markersFactory.clearMarkers();
        markersTypes.clear();
        Log.i("EXCEP", "+ " +  getActivity());
        for (MarkerSettings marker : ((MainActivity) mActivity).getController().getMarkersSettings(Locale.getDefault())) {
            if (types.contains(marker.getType())) {
                markersFactory.createMarker(marker.getLatitude(), marker.getLongitude(),
                        marker.getTitle(), marker.getSnippet(), marker.getHue());
                markersTypes.add(marker.getType());
            }
        }
    }

    public void setMarkers(ArrayList<String> types) {
        removeMarkers();
        buildMarkers(types);
        for (MarkerOptions markerOptions : markersFactory.getMarkers()) {
            markers.add(mMap.addMarker(markerOptions));
        }

    }

    private void restoreMarkers(Bundle savedInstanceState) {
        setMarkers(markersTypes);
    //    isMarkerSelected = savedInstanceState.getBoolean("isMarkerSelected");
  //      myLocationWasClicked = savedInstanceState.getBoolean("myLocationWasClicked");
    }

   private void removeMarkers() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
    }

    public boolean isEmpty(String type) {
       for (String marker: markersTypes) {
            if (marker.equals(type)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_location).setVisible(false);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void displayPromptForEnablingInternet() {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(parentActivity);
        final String actionWifiSettings = Settings.ACTION_WIFI_SETTINGS;
        final String actionWirelessSettings = Settings.ACTION_WIRELESS_SETTINGS;
        final String message = getString(R.string.enable_network);

        builder.setMessage(message)
                .setPositiveButton(getString(R.string.bt_wifi),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                parentActivity.startActivity(new Intent(actionWifiSettings));
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.bt_mobile_network),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                parentActivity.startActivity(new Intent(actionWirelessSettings));
                                dialog.dismiss();
                            }
                        })
                .setNeutralButton(getString(R.string.bt_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                dialog.cancel();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                //((MainActivity)getActivity()).goToFragment(MainActivity.HOME);
                                fragmentTransaction.hide(MapsFragment.this).show(fragmentManager.findFragmentByTag(MainActivity.TABS_TAG)).commit();
                                fragmentTransaction.remove(fragmentManager.findFragmentByTag(MainActivity.MAP_TAG));
                                ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_full_name));
                                ((MainActivity) getActivity()).getMenu().findItem(R.id.action_location).setVisible(false);
                                ((MainActivity) getActivity()).setCurrentFragment(((MainActivity) getActivity()).getTabsFragment());

                            }
                        });
        builder.create().show();
    }

    private void displayPromptForEnablingGPS() {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(parentActivity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = getString(R.string.enable_gps);

        builder.setMessage(message)
                .setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                parentActivity.startActivity(new Intent(action));
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.bt_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                dialog.cancel();
                            }
                        });
        builder.create().show();
    }

    public void backPressed() {

        /*if (isMarkerSelected) {
            isMarkerSelected = false;
            hideMarkerDescription();*/
        if (markerDescriptionVisible) {
            hideMarkerDescription();
        } else {
            goingToHome = true;
            getActivity().onBackPressed();

        }
    }

    public boolean isMarkerDescriptionVisible() {
        return markerDescriptionVisible;
    }

    public void hideMarkerDescription() {
        View parteTotalView = rootView.findViewById(R.id.tela_total);
        FloatingActionsMenu floatingActionsMenu = (FloatingActionsMenu) rootView.findViewById(R.id.multiple_actions);
        parteTotalView.setVisibility(View.INVISIBLE);
        markerDescriptionVisible = false;
        floatingActionsMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (String marker: markersTypes) {
            outState.putBoolean("marker", !isEmpty(marker));

        }


        outState.putBoolean("isMarkerSelected", isMarkerSelected);
        outState.putBoolean("myLocationWasClicked", myLocationWasClicked);
    }
    @Override
    public boolean onMarkerClick(Marker markerAux) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(markerAux.getPosition())
                .zoom(mMap.getCameraPosition().zoom)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        View parteTotalView = rootView.findViewById(R.id.tela_total);
        FloatingActionsMenu floatingActionsMenu = (FloatingActionsMenu) rootView.findViewById(R.id.multiple_actions);
        parteTotalView.setVisibility(View.VISIBLE);
        markerDescriptionVisible = true;
        floatingActionsMenu.setVisibility(View.INVISIBLE);
        isMarkerSelected = true;
        parteTotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        preencheCaixa(markerAux);
        return true;
    }

    private class TracaRota extends AsyncTask<Marker, Void, Location> {

        private Marker markerAsync;
        private ProgressDialog dialog;
        private boolean locationIsNull = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getActivity(),
                    "Aguarde", "Estamos capturando sua posição");
            dialog.setCancelable(true);
        }

        @Override
        protected Location doInBackground(Marker... params) {
            markerAsync = params[0];

            while (locationIsNull) {
                locationIsNull = mLocation == null;
            }
            return mLocation;
        }

        @Override
        protected void onPostExecute(Location myActualLocation) {
            super.onPostExecute(myActualLocation);
            LatLng markerPosition = markerAsync.getPosition();
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=" + myActualLocation.getLatitude() + "," + myActualLocation.getLongitude()
                            + "&daddr=" + markerPosition.latitude + "," + markerPosition.longitude));
            intent.setComponent(new ComponentName("com.google.android.apps.maps",
                    "com.google.android.maps.MapsActivity"));
            myLocation = null;
            dialog.dismiss();
            startActivity(intent);
        }
    }

    public static Context getContext() {
        return mContext;
    }


}
