package com.homies.hovedopgave.Repos;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.interfaces.UserUpdate;
import com.homies.hovedopgave.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Creator: Jonathan
public class UserRepo {
    private static UserRepo userRepo = new UserRepo();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String USERS = "users";
    public List<User> users = new ArrayList();
    private Updatable activity;
    private FirebaseAuth mAuth;
    private String email;
    private String uid;
    private List<String> activePrograms = new ArrayList<>();

    public static UserRepo r(){
        return userRepo;
    }

    public void setup(Updatable a) {
        mAuth = FirebaseAuth.getInstance();
        activity = a;
    }

    public void setup(Updatable a, List<User> list){
        mAuth = FirebaseAuth.getInstance();
        activity = a;
        users = list;
        startListener();
    }

    public void setupNoListener(Updatable a, List<User> list){
        mAuth = FirebaseAuth.getInstance();
        activity = a;
        users = list;
    }

    public void startListener(){
        db.collection(USERS).addSnapshotListener((values, error) -> {
            users.clear();
            if (error == null) {
                for (DocumentSnapshot snap : values.getDocuments()) {
                    String id = snap.getId();
                    Object email = snap.get("email");
                    Object historyList = snap.get("history");
                    Object activePrograms = snap.get("activePrograms");
                    if (id != null) {
                        users.add(new User(id, (String) email, (ArrayList<String>) historyList, (ArrayList<String>) activePrograms));
                    } else {
                        System.out.println("Error getting email");
                    }
                }
            }
            activity.update(0);
        });
    }

    public void addUser(String email, String uid){
        DocumentReference reference = db.collection(USERS).document(uid);
        Map<String, String> map = new HashMap();
        map.put("email", email);
        reference.set(map); // replaces previous values
        System.out.println("Inserted " + reference.getId());
    }

    public void createAuth (String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = new User(email);
                String uid = FirebaseAuth.getInstance().getUid();
                addUser(email, uid);
                activity.update(1);
            } else {
                if (task.getException()instanceof FirebaseAuthUserCollisionException) {
                    activity.update(-1);
                }
            }
            });
    }

    public void checkAuth (String email, String password, Context context) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user.isEmailVerified()) {
                    this.email = email;
                    this.uid = user.getUid();
                    activity.update(1);
                } else {
                    Toast.makeText(context, context.getString(R.string.login_verify), Toast.LENGTH_LONG).show();
                    user.sendEmailVerification();
                }
                
            } else {
                activity.update(-1);
            }
        });
    }

    public void resetPassword (String email, Context context) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, context.getString(R.string.reset_password_succes), Toast.LENGTH_LONG).show();
                activity.update(1);
            } else {
                Toast.makeText(context, context.getString(R.string.reset_password_unsucces), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getActiveProgramList(UserUpdate userUpdate, String uid){
        List<String> tempList = new ArrayList<>();
        if (uid != null) {
            db.collection(USERS).document(uid).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Object o = document.get("activePrograms");
                        if (o != null) {
                            tempList.addAll((ArrayList<String>) o);
                        }
                    }
                }
                this.activePrograms.addAll(tempList);
                userUpdate.activeProgramUpdate(tempList);
            });
        }else {
            System.out.println("UID was null at getActiveProgramList : UserRepo");
        }
    }

    public void addToActiveProgram(String program){
        db.collection(USERS).document(this.uid).update("activePrograms", FieldValue.arrayUnion(program));
    }

    public void addToMyPrograms(String programId){
        db.collection(USERS).document(this.uid).update("myPrograms", FieldValue.arrayUnion(programId));
    }

    public void checkProgramOwnerById(String programId){
        List<String> tempList = new ArrayList<>();
        db.collection(USERS).document(this.uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if (document.exists()){
                    Object o = document.get("myPrograms");
                    if (o != null){
                        tempList.addAll((ArrayList<String>) o);
                        if (tempList.contains(programId)){
                            activity.update(true);
                        }
                    }
                }
            }
            activity.update(false);
        });
    }

    public void deleteProgramFromUser(String id) {
        db.collection(USERS).document(this.uid).update("myPrograms", FieldValue.arrayRemove(id));
        db.collection(USERS).document(this.uid).update("activePrograms", FieldValue.arrayRemove(id));
    }

    public String getEmail(Context context) {
        if (this.email == null) {
            return (context.getString(R.string.user_not_logged_in));
        }
        return email;
    }

    public void getUserById(String userId, Updatable updatable) {
        DocumentReference reference = db.collection(USERS).document(userId);
        reference.get().addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               Map<String, Object> map = task.getResult().getData();
               User user = new User((String) map.get("email"), (List<String>) map.get("activePrograms"),(List<String>) map.get("friendList"), (List<String>) map.get("history"),(List<String>) map.get("myPrograms"), (List<String>) map.get("myExercises"));
               updatable.update(user);
           }
        });
    }

    public void addHistoryToUser(String historyId) {
        db.collection(USERS).document(this.uid).update("history", FieldValue.arrayUnion(historyId));
    }

    public String getLogicalUid(){
        return this.uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}