/* Todo.java

	Purpose:
		
	Description:
		
	History:
		2:20 PM 11/16/15, Created by jumperchen

Copyright (C) 2015 Potix Corporation. All Rights Reserved.
*/
package com.entities;

/**
 * @author jumperchen
 */
public class Todo {
	private String message;
	private boolean done;

	public Todo() {}

	public Todo(String message) {
		this.message = message;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
