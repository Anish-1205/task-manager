package com.example.taskmanager.dto;

public class TaskPatchRequest {

    private Boolean completed;
    private String title;

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
