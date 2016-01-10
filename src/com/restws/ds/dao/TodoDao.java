package com.restws.ds.dao;

import java.util.HashMap;
import java.util.Map;

import com.restws.ds.model.Todo;

//singelton data provide
public enum TodoDao {

	instance;
	
	private Map<String, Todo> contentProvider = new HashMap<String, Todo>();
	
	private TodoDao() {
		Todo todo = new Todo("1", "Learn REST");
	    todo.setDescription("Welcome to http://querydb.blogspot.in");
	    contentProvider.put("1", todo);
	    
	    todo = new Todo("2", "Do something");
	    todo.setDescription("Read complete http://emailsmile.blogspot.com/");
	    contentProvider.put("2", todo);
	    
	}
	
	public Map<String, Todo> getModel(){
	    return contentProvider;
	  }
}
