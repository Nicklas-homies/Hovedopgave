package com.homies.hovedopgave.models;

import java.util.List;

//Made by: All

public class Exercise {
    private String id;
    private String exerciseName;
    private List<String> muscleGroup;
    private List<String> tools;
    private String description;
    private int time; // in minutes

    public Exercise(String exerciseName, List<String> muscleGroup, String description, List<String> tools, int time) {
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.description = description;
        this.tools = tools;
        this.time = time;
    }

    public Exercise(String id, String exerciseName, List<String> muscleGroup, String description, List<String> tools, int time) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.description = description;
        this.tools = tools;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public List<String> getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(List<String> muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTools() {
        return tools;
    }

    public void setTools(List<String> tools) {
        this.tools = tools;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
