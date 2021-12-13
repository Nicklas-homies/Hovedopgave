package com.homies.hovedopgave;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.homies.hovedopgave.Fragments.HomeFragment;
import com.homies.hovedopgave.Login.LoginActivity;
import com.homies.hovedopgave.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepo {
    private static UserRepo userRepo = new UserRepo();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String USERS = "users";
    public List<User> users = new ArrayList();
    private Updatable activity;
    private FirebaseAuth mAuth;
    private String email;
    private String uid;

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

    public void startListener(){
        db.collection(USERS).addSnapshotListener((values, error) -> {
            users.clear();
            if (error == null) {
                for (DocumentSnapshot snap : values.getDocuments()) {
                    Object user = snap.get("email");
                    if (user != null) {
                        users.add(new User());
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

    public String getEmail(Context context) {
        if (this.email == null) {
            return (context.getString(R.string.user_not_logged_in));
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}