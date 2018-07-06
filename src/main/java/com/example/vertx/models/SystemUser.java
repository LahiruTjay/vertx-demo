package com.example.vertx.models;

import io.vertx.core.json.JsonObject;

public class SystemUser {

	private long id;
	private String username;
	private String email;

	public SystemUser() {
	}

	public SystemUser(long id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public SystemUser getSystemUser(JsonObject obj) {
		long id = obj.getLong("id");
		String name = obj.getString("username");
		String email = obj.getString("email");
		return new SystemUser(id, name, email);
	}

}
