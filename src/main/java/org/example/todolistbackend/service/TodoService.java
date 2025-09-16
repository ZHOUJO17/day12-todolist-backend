package org.example.todolistbackend.service;

import org.example.todolistbackend.entity.Todo;
import org.example.todolistbackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    
    @Autowired
    private TodoRepository todoRepository;
    
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }
    
    public Todo updateTodo(Long id, Todo todoDetails) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setText(todoDetails.getText());
                    todo.setDone(todoDetails.getDone());
                    return todoRepository.save(todo);
                })
                .orElse(null);
    }
    
    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}