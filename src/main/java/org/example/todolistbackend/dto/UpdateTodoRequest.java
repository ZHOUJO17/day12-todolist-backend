package org.example.todolistbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateTodoRequest {
    @NotBlank(message = "Text is required and cannot be empty")
    private String text;
    
    private String body;
    
    @NotNull(message = "Done field is required")
    private Boolean done;
    
    public UpdateTodoRequest() {}
    
    public UpdateTodoRequest(String text, Boolean done) {
        this.text = text;
        this.done = done;
    }
    
    public UpdateTodoRequest(String text, String body, Boolean done) {
        this.text = text;
        this.body = body;
        this.done = done;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public Boolean getDone() {
        return done;
    }
    
    public void setDone(Boolean done) {
        this.done = done;
    }
}