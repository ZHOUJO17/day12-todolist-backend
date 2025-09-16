package org.example.todolistbackend.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateTodoRequest {
    @NotBlank(message = "Text is required and cannot be empty")
    private String text;
    
    private String body;
    
    private Boolean done;
    
    public CreateTodoRequest() {}
    
    public CreateTodoRequest(String text) {
        this.text = text;
    }
    
    public CreateTodoRequest(String text, String body) {
        this.text = text;
        this.body = body;
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