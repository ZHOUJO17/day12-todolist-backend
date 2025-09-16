package org.example.todolistbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateTodoRequest {
    @NotBlank(message = "Text is required and cannot be empty")
    private String text;
    
    @NotNull(message = "Done field is required")
    private Boolean done;
    
    public UpdateTodoRequest() {}
    
    public UpdateTodoRequest(String text, Boolean done) {
        this.text = text;
        this.done = done;
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