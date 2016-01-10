package com.restws.ds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.restws.ds.dao.TodoDao;
import com.restws.ds.model.Todo;

@Path("todos")
public class TodoResource {

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	String id;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id}")
	public Todo getTodo(@PathParam("id") String id) {
		System.out.println("getTodo()");
		Todo todo = TodoDao.instance.getModel().get(id);
		if (todo == null)
			throw new RuntimeException("Get: Todo with " + id + " not found");
		return todo;
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{id}")
	public Todo getTodoHTML() {
		System.out.println("getTodoHTML()");
		Todo todo = TodoDao.instance.getModel().get(id);
		if (todo == null)
			throw new RuntimeException("Get: Todo with " + id + " not found");
		return todo;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putTodo(JAXBElement<Todo> todo) {
		System.out.println("putTodo()");
		Todo c = todo.getValue();
		return putAndGetResponse(c);
	}

	private Response putAndGetResponse(Todo todo) {
		Response res;
		if (TodoDao.instance.getModel().containsKey(todo.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		TodoDao.instance.getModel().put(todo.getId(), todo);
		return res;
	}

	@DELETE
	@Path("{id}")
	public void deleteTodo(@PathParam("id") String id) {
		System.out.println("deleteTodo");
		Todo c = TodoDao.instance.getModel().remove(id);
		if (c == null)
			throw new RuntimeException("Delete: Todo with " + id + " not found");
	}

	// Return the list of todos to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Todo> getTodosBrowser() {
		System.out.println("getTodosBrowser");
		List<Todo> todos = new ArrayList<Todo>();
		todos.addAll(TodoDao.instance.getModel().values());
		return todos;
	}

	// Return the list of todos for applications
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Todo> getTodos() {
		System.out.println("getTodosApplication");
		List<Todo> todos = new ArrayList<Todo>();
		todos.addAll(TodoDao.instance.getModel().values());
		return todos;
	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		System.out.println("getCount");
		int count = TodoDao.instance.getModel().size();
		return String.valueOf(count);
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public void newTodo(@FormParam("id") String id,
			 @FormParam("summary") String summary,
			 @FormParam("description") String description,
			 @Context HttpServletResponse servletResponse) throws IOException{
		System.out.println("newTodo");
		Todo todo = new Todo(id, summary);
	    if (description != null) {
	      todo.setDescription(description);
	    }
	    
	    TodoDao.instance.getModel().put(id, todo);
	    servletResponse.sendRedirect("../Welcome.html");
	}
}
