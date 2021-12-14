package com.homies.hovedopgave.models;

import java.time.LocalDate;

public class History {
    String id;
    String programId;
    LocalDate completedDate;
    String userId;

    public History(String programId, LocalDate completedDate) {
        this.programId = programId;
        this.completedDate = completedDate;
    }

    public History(String id, String programId, LocalDate completedDate) {
        this.id = id;
        this.programId = programId;
        this.completedDate = completedDate;
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
}
