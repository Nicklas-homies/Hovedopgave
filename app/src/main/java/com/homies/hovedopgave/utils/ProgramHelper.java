package com.homies.hovedopgave.utils;

import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Creator: Jonathan
//Hjælperklasse til at håndtere lists og maps
public class ProgramHelper {

    public static HashMap<String, Exercise> convertExerciseListToMap(ArrayList<Exercise> list){
        HashMap<String, Exercise> toReturn = new HashMap();
        for (Exercise exercise : list) {
            toReturn.put(exercise.getExerciseName(), exercise);
        }
        return toReturn;
    }

    public static ArrayList<Program> generateRealProgramList(List<Program> programList, Map<String, Exercise> exerciseMap){
        ArrayList<Program> toReturn = new ArrayList<>();
        List<Exercise> tempExerciseList;
        for (Program p : programList) {
            tempExerciseList = new ArrayList<>();
            for (String s : p.getExerciseListString()) {
                if (exerciseMap.containsKey(s)){
                    tempExerciseList.add(exerciseMap.get(s));
                }
            }
            toReturn.add(new Program(p.getId(), p.getProgramName(), tempExerciseList));
        }
        System.out.println("toReturn: " + toReturn.toString());
        return toReturn;
    }
}
