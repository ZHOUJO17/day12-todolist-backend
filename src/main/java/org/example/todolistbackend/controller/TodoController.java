package org.example.todolistbackend.controller;

import org.example.todolistbackend.dto.CreateTodoRequest;
import org.example.todolistbackend.dto.UpdateTodoRequest;
import org.example.todolistbackend.entity.Todo;
import org.example.todolistbackend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "*")
@Validated
public class TodoController {
    
    @Autowired
    private TodoService todoService;
    
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }
    
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody CreateTodoRequest request) {
        Todo todo = new Todo();
        todo.setText(request.getText());
        todo.setBody(request.getBody());
        todo.setDone(false);
        
        Todo createdTodo = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todo = todoService.getTodoById(id);
        if (todo != null) {
            return ResponseEntity.ok(todo);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody UpdateTodoRequest request) {
        Todo todoDetails = new Todo();
        todoDetails.setText(request.getText());
        todoDetails.setBody(request.getBody());
        todoDetails.setDone(request.getDone());
        
        Todo updatedTodo = todoService.updateTodo(id, todoDetails);
        if (updatedTodo != null) {
            return ResponseEntity.ok(updatedTodo);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        boolean deleted = todoService.deleteTodo(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}