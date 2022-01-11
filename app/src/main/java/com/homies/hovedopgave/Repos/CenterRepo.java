package com.homies.hovedopgave.Repos;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.maps.model.LatLng;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.models.Center;

import java.util.ArrayList;
import java.util.List;

public class CenterRepo {
    private static CenterRepo repo = new CenterRepo();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String CENTERS = "centers";
    public List<Center> centers = new ArrayList();
    private Updatable activity;

    public static CenterRepo r(){
        return repo;
    }

    public void setup(Updatable activity, List<Center> list){
        this.activity = activity;
        this.centers = list;
        startListener();
    }

    private void startListener() {
        db.collection(CENTERS).addSnapshotListener((value, error) -> {
            centers.clear();
            if (error == null){
                for (DocumentSnapshot snapshot : value.getDocuments()){
                    String id = snapshot.getId();
                    String title = (String) snapshot.get("title");
                    String open = (String) snapshot.get("open");
                    String close = (String) snapshot.get("close");
                    GeoPoint pos = snapshot.getGeoPoint("latLng");
                    LatLng latLng = new LatLng(pos.getLatitude(), pos.getLongitude());

                    if (id != null){
                        centers.add(new Center(id, title, open, close, latLng));
                    }
                }
            }
            activity.update(centers);
        });
    }
}
