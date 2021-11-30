package com.homies.hovedopgave;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.homies.hovedopgave.Fragments.HistoryFragment;
import com.homies.hovedopgave.Fragments.HomeFragment;
import com.homies.hovedopgave.Fragments.ProgramsFragment;
import com.homies.hovedopgave.Fragments.SettingsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repo {
    private static Repo repo = new Repo();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION = "users";
    public List<String> users = new ArrayList();
    private Updatable activity;

    public static Repo r(){
        return repo;
    }

    public void setup(Updatable a, List<String> list){
        activity = a;
        users = list;
        startListener();
    }

    public void startListener(){
        db.collection(COLLECTION).addSnapshotListener((values, error) -> {
            users.clear();
            if (error == null) {
                for (DocumentSnapshot snap : values.getDocuments()) {
                    Object user = snap.get("user");
                    if (user != null) {
                        users.add(user.toString());
                    } else {
                        System.out.println("Error getting users");
                    }
                }
            }
            activity.update(null);
        });
    }

    public void addUser(String username){
        DocumentReference reference = db.collection(COLLECTION).document(username);
        Map<String, String> map = new HashMap();
        map.put("user", username);
        reference.set(map); // replaces previous values
        System.out.println("Inserted " + reference.getId());
    }
}
