package org.example.todolistbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String text;
    
    @Column(columnDefinition = "TEXT")
    private String body;
    
    @Column(nullable = false)
    private Boolean done = false;
    
    public Todo() {}
    
    public Todo(String text, Boolean done) {
        this.text = text;
        this.done = done;
    }
    
    public Todo(String text, String body, Boolean done) {
        this.text = text;
        this.body = body;
        this.done = done;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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