package com.example.vertx.service;

import com.example.vertx.dto.CommonHttpResponse;

public interface UserService {

	CommonHttpResponse getAllUsers();

	CommonHttpResponse getUserById(long id);

}
