package com.homies.hovedopgave.models;

import java.util.ArrayList;

public class User {
    String username;
    String password; //need to be hashed
    ArrayList<Exercise> userExercises;
    ArrayList<Program> userPrograms;
    ArrayList<String> fiends;

    public User(String username, String password, ArrayList<Exercise> userExercises, ArrayList<Program> userPrograms, ArrayList<String> fiends) {
        this.username = username;
        this.password = password;
        this.userExercises = userExercises;
        this.userPrograms = userPrograms;
        this.fiends = fiends;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Exercise> getUserExercises() {
        return userExercises;
    }

    public void setUserExercises(ArrayList<Exercise> userExercises) {
        this.userExercises = userExercises;
    }

    public ArrayList<Program> getUserPrograms() {
        return userPrograms;
    }

    public void setUserPrograms(ArrayList<Program> userPrograms) {
        this.userPrograms = userPrograms;
    }

    public ArrayList<String> getFiends() {
        return fiends;
    }

    public void setFiends(ArrayList<String> fiends) {
        this.fiends = fiends;
    }
}
