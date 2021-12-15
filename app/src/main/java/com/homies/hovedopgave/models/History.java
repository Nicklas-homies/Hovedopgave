package com.homies.hovedopgave.models;

import java.time.LocalDate;

/* Written by **Jacob Ravn** jaco8748 */
public class History {
    String id;
    String programId;
    String programName;
    LocalDate completedDate;
    String userId;

    public History(String programId, String programName, LocalDate completedDate, String userId) {
        this.programId = programId;
        this.programName = programName;
        this.completedDate = completedDate;
        this.userId = userId;
    }

    public History(String id, String programId, String programName, LocalDate completedDate, String userId) {
        this.id = id;
        this.programId = programId;
        this.programName = programName;
        this.completedDate = completedDate;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getProgramName() {
        return programName;
    }

    @Override
    public String toString() {
        return "History{" +
                "id='" + id + '\'' +
                ", programId='" + programId + '\'' +
                ", programName='" + programName + '\'' +
                ", completedDate=" + completedDate +
                ", userId='" + userId + '\'' +
                '}';
    }
}
