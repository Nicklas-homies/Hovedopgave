package com.homies.hovedopgave.Repos;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.models.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Creator: Jonathan
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

    public void saveProgram(Program program){
        Map<String, Object> programToAdd = new HashMap<>();
        programToAdd.put("programName", program.getProgramName());
        programToAdd.put("exerciseList", program.getExerciseListString());

        db.collection(PROGRAMS).add(programToAdd);
    }

    public void getProgramById(String id, Updatable updatable){
        activity = updatable;
        db.collection(PROGRAMS).document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                tempReturnProgram = new Program(document.getId(), (ArrayList) document.get("exerciseList"), (String) document.get("programName"));
            }
            activity.update(tempReturnProgram);
        });
    }
}
