package com.homies.hovedopgave.models;

import java.util.List;

//Creator: Alle
public class User {

    public String id;
    public String email;
    public List<String> activePrograms;
    public List<String> friendList;
    public List<String> history;
    public List<String> myPrograms;
    public List<String> myExercises;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String id, String email, List<String> history, List<String> activePrograms) {
        this.id = id;
        this.email = email;
        this.history = history;
        this.activePrograms = activePrograms;
    }

    public User(String email, List<String> activePrograms, List<String> friendList, List<String> history, List<String> myPrograms, List<String> myExercises) {
        this.email = email;
        this.activePrograms = activePrograms;
        this.friendList = friendList;
        this.history = history;
        this.myPrograms = myPrograms;
        this.myExercises = myExercises;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getActivePrograms() {
        return activePrograms;
    }

    public void setActivePrograms(List<String> activePrograms) {
        this.activePrograms = activePrograms;
    }

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

    public List<String> getMyPrograms() {
        return myPrograms;
    }

    public void setMyPrograms(List<String> myPrograms) {
        this.myPrograms = myPrograms;
    }

    public List<String> getMyExercises() {
        return myExercises;
    }

    public void setMyExercises(List<String> myExercises) {
        this.myExercises = myExercises;
    }
}
