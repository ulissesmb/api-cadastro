package com.ulissesmb.response;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<R> {

	private _Error error;
	private List<R> data = null;
	private int status;
	private String phrase;
	private String message;
	private final Long timestamp;

	public Response() {
		this.timestamp = new Date().getTime();
		status = HttpStatus.OK.value();
		phrase = HttpStatus.OK.getReasonPhrase();
	}

	public Response(String message) {
		this();
		this.setMessage(message);
	}

	public Response(_Error error) {
		this();
		this.setError(error);
	}

	public Response(List<R> data) {
		this();
		this.setData(data);
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public List<R> getData() {
		return data;
	}

	public void setData(List<R> data) {
		if (data == null) {
			throw new IllegalArgumentException("data == null");
		}
		this.data = data;
	}

	public _Error getError() {
		return error;
	}

	public void setError(_Error error) {
		if (error == null) {
			throw new IllegalArgumentException("error == null");
		}
		this.error = error;
	}

	public void setHttp(HttpStatus http) {
		if (http == null) {
			throw new IllegalArgumentException("http == null");
		}
		this.status = http.value();
		this.phrase = http.getReasonPhrase();
	}

	public int getStatus() {
		return status;
	}

	public String getPhrase() {
		return phrase;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
