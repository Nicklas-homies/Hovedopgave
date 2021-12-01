package com.homies.hovedopgave.Repos;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramRepo {
    private static ProgramRepo repo = new ProgramRepo();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String PROGRAMS = "programs";
    public List<Program> programs = new ArrayList();
    public List<Program> programsExerciseStringFormat = new ArrayList();
    private Updatable activity;
    private Program tempReturnProgram;

    public static ProgramRepo pr(){
        return repo;
    }

    public void setup(Updatable activity, List<Program> list){
        this.activity = activity;
        this.programs = list;
        startListener();
    }

    public void startListener(){
        db.collection(PROGRAMS).addSnapshotListener((values, error) -> {
          programsExerciseStringFormat.clear();
          if (error == null){
              for (DocumentSnapshot snapshot : values.getDocuments()){
                  String id = snapshot.getId();
                  Object name = snapshot.get("programName");
                  ArrayList<String> exerciseListString = (ArrayList<String>) snapshot.get("exerciseList");

                  if (id != null){
                      programsExerciseStringFormat.add(new Program(id, exerciseListString, name.toString()));
                  }
              }
          }
          activity.update(programsExerciseStringFormat);
        });
    }

    public void getProgramById(String id, Updatable updatable){
        activity = updatable;
        db.collection(PROGRAMS).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    tempReturnProgram = new Program(document.getId(), (ArrayList) document.get("exerciseList"), (String) document.get("programName"));
                }
                activity.update(tempReturnProgram);
            }
        });
    }

    public HashMap<String, Exercise> convertArrayListToHashMap(ArrayList<Exercise> list){
        HashMap<String, Exercise> toReturn = new HashMap();
        for (Exercise exercise : list) {
            toReturn.put(exercise.getExerciseName(), exercise);
        }
        return toReturn;
    }
}
