package org.example.todolistbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.todolistbackend.entity.Todo;
import org.example.todolistbackend.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class TodoListBackendApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		todoRepository.deleteAll();
	}

	@Test
	void testGetAllTodos_WhenNoTodosExist_ShouldReturnEmptyArray() throws Exception {
		// AC 1: List all todos when none exist
		mockMvc.perform(get("/todos"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void testGetAllTodos_WhenOneTodoExists_ShouldReturnTodoArray() throws Exception {
		// AC 1: List one todo when only one exists
		// Given: the storage contains the following todos
		Todo todo = new Todo("Buy milk", false);
		todoRepository.save(todo);

		// When: a client GETs /todos
		// Then: respond 200 OK
		// And: the response body is a JSON array with 1 items
		// And: the array contains objects equivalent to those todos (id, text, done)
		mockMvc.perform(get("/todos"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", notNullValue()))
				.andExpect(jsonPath("$[0].text", is("Buy milk")))
				.andExpect(jsonPath("$[0].done", is(false)));
	}

	@Test
	void testCreateTodo() throws Exception {
		Todo newTodo = new Todo("Test todo", false);

		mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newTodo)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.text", is("Test todo")))
				.andExpect(jsonPath("$.done", is(false)));
	}

	@Test
	void testGetTodoById() throws Exception {
		Todo todo = new Todo("Test todo", false);
		Todo savedTodo = todoRepository.save(todo);

		mockMvc.perform(get("/todos/" + savedTodo.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(savedTodo.getId().intValue())))
				.andExpect(jsonPath("$.text", is("Test todo")))
				.andExpect(jsonPath("$.done", is(false)));
	}

	@Test
	void testUpdateTodo() throws Exception {
		Todo todo = new Todo("Original text", false);
		Todo savedTodo = todoRepository.save(todo);

		Todo updatedTodo = new Todo("Updated text", true);

		mockMvc.perform(put("/todos/" + savedTodo.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedTodo)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(savedTodo.getId().intValue())))
				.andExpect(jsonPath("$.text", is("Updated text")))
				.andExpect(jsonPath("$.done", is(true)));
	}

	@Test
	void testDeleteTodo() throws Exception {
		Todo todo = new Todo("Test todo", false);
		Todo savedTodo = todoRepository.save(todo);

		mockMvc.perform(delete("/todos/" + savedTodo.getId()))
				.andExpect(status().isNoContent());

		// Verify the todo is deleted
		mockMvc.perform(get("/todos/" + savedTodo.getId()))
				.andExpect(status().isNotFound());
	}
}