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
	void should_return_empty_array_when_get_all_todos_given_no_todos_exist() throws Exception {
		mockMvc.perform(get("/todos"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void should_return_todo_array_when_get_all_todos_given_one_todo_exists() throws Exception {
		Todo todo = new Todo("Buy milk", false);
		todoRepository.save(todo);

		mockMvc.perform(get("/todos"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", notNullValue()))
				.andExpect(jsonPath("$[0].text", is("Buy milk")))
				.andExpect(jsonPath("$[0].done", is(false)));
	}

	@Test
	void should_return_todo_when_create_given_valid_params() throws Exception {
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
	void should_return_todo_when_get_todo_by_id_given_valid_id() throws Exception {
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
	void should_return_updated_todo_when_put_update_todo_given_valid_data() throws Exception {
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
	void should_return_no_content_when_delete_todo_given_valid_id() throws Exception {
		Todo todo = new Todo("Test todo", false);
		Todo savedTodo = todoRepository.save(todo);

		mockMvc.perform(delete("/todos/" + savedTodo.getId()))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/todos/" + savedTodo.getId()))
				.andExpect(status().isNotFound());
	}
}