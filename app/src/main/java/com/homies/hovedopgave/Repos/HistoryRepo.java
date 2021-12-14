package com.homies.hovedopgave.Repos;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.models.History;

import java.time.LocalDate;
import java.util.ArrayList;

public class HistoryRepo {
    private static HistoryRepo repo = new HistoryRepo();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String HISTORY = "HISTORY";
    public ArrayList<History> histories = new ArrayList<>();
    private Updatable activity;

    public static HistoryRepo r() { return repo; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setup(Updatable activity, ArrayList<History> list, ArrayList<String> userHistoryIdList) {
        this.activity = activity;
        this.histories = list;
        startListener(userHistoryIdList);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startListener(ArrayList<String> userHistoryIdList) {
        if (userHistoryIdList.size() == 0) {
            // fail
            activity.update(-1);
            return;
        }
        db.collection(HISTORY).addSnapshotListener(((value, error) -> {
            if (error == null) {
                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    String id = snapshot.getId();
                    if (id != null) {
                        // only user history is wanted in all (I think) cases.
                        if (!userHistoryIdList.contains(id)) {
                            continue;
                        }
                        String date = (String) snapshot.get("date");
                        LocalDate localDate = LocalDate.parse(date);
                        histories.add(new History(id, (String) snapshot.get("programId"), localDate));
                    }
                }
                // success
                activity.update(1);
            }
        }));
    }

}
