package com.nearby.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nearby.R;
import com.nearby.adapter.Nearby;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private double lat, lng;
    private List<Nearby> nearbyList;
    private List<LatLng> latLngs = null;

    private boolean mMapReady = false;
    private FetchLocation fetchLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        nearbyList = new ArrayList<>();
        latLngs = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        nearbyList = bundle.getParcelableArrayList("animate_list");
        for (Nearby nearby : nearbyList){
          latLngs.add(new LatLng(Double.parseDouble(nearby.getLat()),Double.parseDouble(nearby.getLng())));
        }

        fetchLocation = FetchLocation.getInstance(getBaseContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapReady = true;
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        position(latLngs);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void position(final List<LatLng> latLng){

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(latLngs.get(0));
        builder.include(latLngs.get(latLngs.size()-1));
        builder.build();
        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,200);
        mMap.moveCamera(cu);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

        for(int i=0; i < latLng.size(); i++){
            mMap.addMarker(new MarkerOptions().position(latLng.get(i)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_icon_64)));
        }
        startAnim(latLng);

    }

    private void startAnim(List<LatLng> bangaloreRoute){
        if(mMap != null) {
            MapAnimator.getInstance().animateRoute(mMap, bangaloreRoute);
        } else {
            Toast.makeText(getApplicationContext(), "Map not ready", Toast.LENGTH_LONG).show();
        }
    }

}
