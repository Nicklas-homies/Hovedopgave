package com.homies.hovedopgave.Fragments;

import android.graphics.Paint;
import android.location.Geocoder;
import android.location.Address;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.CenterRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.models.Center;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements Updatable {

    private GoogleMap myMap;
    private MapView mapView;
    private ArrayList<Center> centerList = new ArrayList();

    private TextView allText, satsText, fwText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mapView.getMapAsync(googleMap -> {
            myMap = googleMap;
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.70186, 12.47624), 10.8f));
        });

        allText = view.findViewById(R.id.all_text);
        satsText = view.findViewById(R.id.sats_text);
        fwText = view.findViewById(R.id.fw_text);

        allText.setOnClickListener(v -> filterMarkers(centerList, "all"));
        satsText.setOnClickListener(v -> filterMarkers(centerList, "SATS"));
        fwText.setOnClickListener(v -> filterMarkers(centerList, "Fitness World"));

        CenterRepo.r().setup(this, centerList);

        return view;
    }

    @Override
    public void update(Object o) {
        centerList = (ArrayList<Center>) o;

        filterMarkers(centerList, "all");
    }

    public void filterMarkers(List<Center> list, String filterBy){
        myMap.clear();

        switch (filterBy){
            case "all":
                allText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                satsText.setPaintFlags(0);
                fwText.setPaintFlags(0);
                break;
            case "SATS":
                allText.setPaintFlags(0);
                satsText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                fwText.setPaintFlags(0);
                break;
            case "Fitness World":
                allText.setPaintFlags(0);
                satsText.setPaintFlags(0);
                fwText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                break;
        }

        for (Center center : list) {
            if (center.getTitle().equals(filterBy) || filterBy.equals("all")) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(center.getTitle())
                        .position(center.getLatLng())
                        .snippet(center.getOpen() + " - " + center.getClose());
                myMap.addMarker(markerOptions);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}