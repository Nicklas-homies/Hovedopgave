package com.homies.hovedopgave.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Program {
    private String id;
    private String programName;
    private List<Exercise> exerciseList;
    private Set<String> muscleGroup = new HashSet();
    private Set<String> toolsList = new HashSet();
    private int time = 0; //in minutes


    public Program(String id, String programName, List<Exercise> exerciseList) {
        this.id = id;
        this.programName = programName;
        this.exerciseList = exerciseList;
        populateMuscleGroup();
        populateToolsList();
        calculateTime();
    }

    public Program(String programName, List<Exercise> exerciseList) {
        this.programName = programName;
        this.exerciseList = exerciseList;
        populateMuscleGroup();
        populateToolsList();
        calculateTime();
    }

    public void populateMuscleGroup(){
        for (Exercise exercise : this.exerciseList) {
            muscleGroup.addAll(exercise.getMuscleGroup());
        }
    }

    public void populateToolsList(){
        for (Exercise exercise : this.exerciseList) {
            toolsList.addAll(exercise.getTools());
        }
    }

    public void calculateTime(){
        for (Exercise exercise : this.exerciseList) {
            this.time += exercise.getTime();
        }
    }

    public String getId() {
        return id;
    }

    public String getProgramName() {
        return programName;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public Set<String> getMuscleGroup() {
        return muscleGroup;
    }

    public Set<String> getToolsList() {
        return toolsList;
    }

    public int getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void addExercise(Exercise exercise){
        this.exerciseList.add(exercise);
    }
}
