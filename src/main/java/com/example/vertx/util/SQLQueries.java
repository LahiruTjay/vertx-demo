package com.example.vertx.util;

public class SQLQueries {

	public static final String SQL_INSERT_USER = "INSERT INTO user (username, email) VALUES (?, ?)";
	public static final String SQL_UPDATE_USER = "UPDATE user SET username = ?, email = ? WHERE id = ?";
	public static final String SQL_SELECT_ALL_USERS = "SELECT * FROM user";
	public static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
	public static final String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE id = ?";
	public static final String SQL_DELETE_ALL_USERS = "DELETE FROM user";

}
