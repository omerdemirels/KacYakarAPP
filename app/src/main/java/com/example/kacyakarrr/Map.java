package com.example.kacyakarrr;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.*;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;

    SupportMapFragment mapFragment;
    SearchView searchView;
    SearchView searchView2;
    public static float results[];
    Address address;
    Address address2;
    double lat1 = 39.925533;
    double long1 = 32.866287;
    double lat2 = 41.015137;
    double long2 = 28.979530;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        results =  new float[10];
        searchView = findViewById(R.id.sv_location);
        searchView2 = findViewById(R.id.sv_location2);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address>addressList = null;

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(Map.this);
                    try{
                        addressList = geocoder.getFromLocationName(location, 1);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    address = addressList.get(0);
                    lat1 = address.getLatitude();
                    long1 = address.getLongitude();
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location2 = searchView2.getQuery().toString();
                List<Address>addressList2 = null;

                if(location2 != null || !location2.equals("")){
                    Geocoder geocoder2 = new Geocoder(Map.this);
                    try{
                        addressList2 = geocoder2.getFromLocationName(location2, 1);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    address2 = addressList2.get(0);
                    lat2 = address2.getLatitude();
                    long2 = address2.getLongitude();

                    double newLat = address2.getLatitude();
                    System.out.println("NEW LAT IS : ");
                    System.out.println(newLat);
                    LatLng latLng2 = new LatLng(address2.getLatitude(), address2.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng2).title(location2));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2,8));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    public void onClick(View view) {

        System.out.println(lat1 + "   " + long1 + "  " + lat2 + "  " + long2);
        Location.distanceBetween(lat1, long1, lat2, long2, results );
        startActivity(new Intent(this, Result.class));
    }










}