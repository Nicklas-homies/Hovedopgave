package com.homies.hovedopgave;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.homies.hovedopgave.models.Exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseRepo {
    private static ExerciseRepo repo = new ExerciseRepo();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION = "exercises";
    public ArrayList<Exercise> exercises = new ArrayList();
    private Updatable activity;

    public static ExerciseRepo r() { return repo; }

    public void setup(Updatable a, ArrayList<Exercise> list){
        activity = a;
        exercises = list;
        startListener();
    }

    public void startListener(){
        db.collection(COLLECTION).addSnapshotListener((values, error) -> {
            exercises.clear();
            if (error == null) {
                for (DocumentSnapshot snap : values.getDocuments()) {
                    Map<String, Object> mapExercise = snap.getData();
                    if (mapExercise.size() != 0) {
                        ArrayList<String> muscleGroup = new ArrayList<>();
                        muscleGroup.add((String) mapExercise.get("muscleGroup"));
                        ArrayList<String> tools = new ArrayList<>();
                        tools.add((String) mapExercise.get("tools"));
                        exercises.add(new Exercise(snap.getId(), muscleGroup, tools,
                                (String) mapExercise.get("description"),Integer.valueOf((String) mapExercise.get("time")) ));
                    } else {
                        System.out.println("Error getting users");
                    }
                }
            }
            activity.update(null);
        });
    }

    public void addExercise(Exercise exercise){
        DocumentReference reference = db.collection(COLLECTION).document(exercise.getExerciseName());
        Map<String, String> map = new HashMap();
        map.put("exercise", exercise.getExerciseName());
        map.put("muscleGroup", exercise.getMuscleGroup().toString());
        map.put("tools", exercise.getTools().toString());
        map.put("description", exercise.getDescription());
        map.put("time", String.valueOf(exercise.getTime()));
        reference.set(map); // replaces previous values
        System.out.println("Inserted " + reference.getId());
    }
}
