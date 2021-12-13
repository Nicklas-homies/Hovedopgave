package com.homies.hovedopgave.models;

import java.util.List;

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