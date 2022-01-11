package com.homies.hovedopgave.models;

import com.google.android.gms.maps.model.LatLng;

public class Center {
    private String id;
    private String title;
    private String open;
    private String close;
    private LatLng latLng;

    public Center() {
    }

    public Center(String id, String title, String open, String close, LatLng latLng) {
        this.id = id;
        this.title = title;
        this.open = open;
        this.close = close;
        this.latLng = latLng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
