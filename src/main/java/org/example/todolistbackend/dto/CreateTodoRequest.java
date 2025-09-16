package org.example.todolistbackend.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateTodoRequest {
    @NotBlank(message = "Text is required and cannot be empty")
    private String text;
    
    private Boolean done;
    
    public CreateTodoRequest() {}
    
    public CreateTodoRequest(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public Boolean getDone() {
        return done;
    }
    
    public void setDone(Boolean done) {
        this.done = done;
    }
}