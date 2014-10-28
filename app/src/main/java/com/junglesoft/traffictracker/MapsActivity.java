package com.junglesoft.traffictracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {

                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {

                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                        if (marker.getTitle().equalsIgnoreCase("Home")) {
                            prefEditor.putString("homeLocationLatitude", String.valueOf(marker.getPosition().latitude));
                            prefEditor.putString("homeLocationLongitude", String.valueOf(marker.getPosition().longitude));
                        }
                        if (marker.getTitle().equalsIgnoreCase("Work")) {
                            prefEditor.putString("workLocationLatitude", String.valueOf(marker.getPosition().latitude));
                            prefEditor.putString("workLocationLongitude", String.valueOf(marker.getPosition().longitude));
                        }
                        prefEditor.commit();
                    }
                });
            }

        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     * location info
     * work: 6.428876,3.429083
     * home: 6.644413,3.349196
     */
    private void setUpMap() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        double homeLocationLongitude = Double.valueOf(sharedPreferences.getString("homeLocationLongitude", "0.0"));
        double homeLocationLatitude = Double.valueOf(sharedPreferences.getString("homeLocationLatitude", "0.0"));
        double workLocationLongitude = Double.valueOf(sharedPreferences.getString("workLocationLongitude", "0.0"));
        double workLocationLatitude = Double.valueOf(sharedPreferences.getString("workLocationLatitude", "0.0"));
//
        LatLng llHome = new LatLng(homeLocationLatitude, homeLocationLongitude);
        LatLng llWork = new LatLng(workLocationLatitude, workLocationLongitude);
        Marker homeMarker = mMap.addMarker(new MarkerOptions().position(llHome).title("Home"));
        Marker workMarker = mMap.addMarker(new MarkerOptions().position(llWork).title("Work"));

        homeMarker.showInfoWindow();
        homeMarker.setDraggable(true);
        homeMarker.setTitle("Home");
        homeMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        workMarker.showInfoWindow();
        workMarker.setDraggable(true);
        workMarker.setTitle("Work");
        workMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        LatLngBounds latLngBounds;
        if (llHome.latitude > llWork.latitude) {
            latLngBounds = new LatLngBounds(llWork, llHome);
        } else {
            latLngBounds = new LatLngBounds(llHome, llWork);
        }


        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,0));
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(4));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(llHome, 10));
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(getApplicationContext(), "Locating you...", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}
