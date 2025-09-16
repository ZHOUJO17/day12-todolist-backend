package org.example.todolistbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TodoListBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListBackendApplication.class, args);
	}

}