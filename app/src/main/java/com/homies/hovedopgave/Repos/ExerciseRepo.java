package com.homies.hovedopgave.Repos;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.interfaces.ExerciseUpdate;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.programs.ProgramDescriptionActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExerciseRepo {
    private static ExerciseRepo repo = new ExerciseRepo();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String EXERCISES = "exercises";
    public ArrayList<Exercise> exercises = new ArrayList();
    private Updatable activity;

    public static ExerciseRepo r() { return repo; }

    public void setup(Updatable a, ArrayList<Exercise> list){
        activity = a;
        exercises = list;
        startListener();
    }

    // used when getting by id instead of everything.
    public void setup(Updatable a) {
        activity = a;
    }

    public void startListener(){
        db.collection(EXERCISES).addSnapshotListener((values, error) -> {
            exercises.clear();
            if (error == null) {
                for (DocumentSnapshot snap : values.getDocuments()) {
                    Map<String, Object> mapExercise = snap.getData();
                    if (mapExercise.size() != 0) {
                        ArrayList<String> muscleGroup;
                        muscleGroup = (ArrayList<String>) mapExercise.get("muscleGroup");
                        ArrayList<String> tools;
                        tools = (ArrayList<String>) mapExercise.get("tools");
                        exercises.add(new Exercise(snap.getId(), muscleGroup, tools, (String) mapExercise.get("description"), Integer.valueOf((String) mapExercise.get("time")) ));
                    } else {
                        System.out.println("Error getting users");
                    }
                }
            }
            activity.update(null);
        });
    }

    //Creator: Jonathan
    //Bruges til at få exercises i programsFragment, originalt tænkt var dette ikke en listener derfor ligner den startListener
    public void tempStartListener(ExerciseUpdate pFragment){
        db.collection(EXERCISES).addSnapshotListener((values, error) -> {
            exercises.clear();
            if (error == null) {
                for (DocumentSnapshot snap : values.getDocuments()) {
                    Map<String, Object> mapExercise = snap.getData();
                    if (mapExercise.size() != 0) {
                        ArrayList<String> muscleGroup = new ArrayList<>();
                        muscleGroup.addAll((ArrayList<String>) mapExercise.get("muscleGroup"));
                        ArrayList<String> tools = new ArrayList<>();
                        tools.addAll((ArrayList<String>) mapExercise.get("tools"));
                        exercises.add(new Exercise(snap.getId(), muscleGroup, tools, (String) mapExercise.get("description"), Integer.valueOf((String) mapExercise.get("time"))));
                    } else {
                        System.out.println("Error getting users");
                    }
                }
            }
            pFragment.exerciseUpdate(exercises);
        });
    }

    //Creator: Jonathan
    //Bruges til at hente exercises ud fra en liste med id, som bruges i sammenhæng med at hente fra programs
    public void getExercisesById(ArrayList<String> idList){
        db.collection(EXERCISES).get().addOnCompleteListener(task -> {
            ArrayList<Exercise> exList = new ArrayList<>();
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot qs : task.getResult()) {
                    Map<String, Object> mapExercise = qs.getData();
                    if (idList.contains(qs.getId())){
                        ArrayList<String> muscleGroup = new ArrayList<>();
                        muscleGroup.addAll((ArrayList<String>) mapExercise.get("muscleGroup"));
                        ArrayList<String> tools = new ArrayList<>();
                        tools.addAll((ArrayList<String>) mapExercise.get("tools"));
                        exList.add(new Exercise(qs.getId(), muscleGroup, tools, (String) mapExercise.get("description"), Integer.valueOf((String) mapExercise.get("time"))));
                    }
                }
            }
            activity.update(exList);
        });
    }

    //Creator: Jonathan
    //Bruges til at hente exercises ud fra en liste med id, som bruges i sammenhæng med at hente fra programs
    public void getExercisesById(ArrayList<String> idList, ExerciseUpdate updateActivity){
        db.collection(EXERCISES).get().addOnCompleteListener(task -> {
            ArrayList<Exercise> exList = new ArrayList<>();
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot qs : task.getResult()) {
                    Map<String, Object> mapExercise = qs.getData();
                    if (idList.contains(qs.getId())){
                        ArrayList<String> muscleGroup = new ArrayList<>();
                        muscleGroup.addAll((ArrayList<String>) mapExercise.get("muscleGroup"));
                        ArrayList<String> tools = new ArrayList<>();
                        tools.addAll((ArrayList<String>) mapExercise.get("tools"));
                        exList.add(new Exercise(qs.getId(), muscleGroup, tools, (String) mapExercise.get("description"), Integer.valueOf((String) mapExercise.get("time"))));
                    }
                }
            }
            updateActivity.exerciseUpdate(exList);
        });
    }

    public void addExercise(Exercise exercise){
        DocumentReference reference = db.collection(EXERCISES).document(exercise.getExerciseName());
        Map<String, String> map = new HashMap();
        map.put("exercise", exercise.getExerciseName());
        map.put("muscleGroup", exercise.getMuscleGroup().toString());
        map.put("tools", exercise.getTools().toString());
        map.put("description", exercise.getDescription());
        map.put("time", String.valueOf(exercise.getTime()));
        reference.set(map); // replaces previous values
        System.out.println("Inserted " + reference.getId());
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }
}