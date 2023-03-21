package com.project.search.dto;

import lombok.Getter;

@Getter
public class ErrorDto {
	int status;
	String message;
	public ErrorDto(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
