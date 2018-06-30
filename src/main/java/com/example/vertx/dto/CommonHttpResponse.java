package com.example.vertx.dto;

public class CommonHttpResponse {

	private String code;
	private String message;
	private Object data;

	public CommonHttpResponse() {
	}

	public CommonHttpResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public CommonHttpResponse(String code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
