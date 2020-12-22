package com.ulissesmb.response;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBuilder<R> {

	private BuilderError builderError = null;
	private BuilderSuccess builderSuccess = null;

	public BuilderSuccess withData(List<R> data) {
		this.builderSuccess = new BuilderSuccess(data);
		return this.builderSuccess;
	}

	public BuilderSuccess withData(R data) {
		this.builderSuccess = new BuilderSuccess(data);
		return this.builderSuccess;
	}

	public BuilderSuccess withMessage(String message) {
		builderSuccess = new BuilderSuccess(message);
		return builderSuccess;
	}

	public BuilderError withError(_Error error) {
		builderError = new BuilderError(error);
		return builderError;
	}

	public ResponseBuilder() {

	}

	public abstract class Builder {

		protected Response<R> response;

		public Builder withHttp(HttpStatus http) {
			response.setHttp(http);
			return this;
		}

		public ResponseEntity<Response<R>> build() {
			return ResponseEntity.status(response.getStatus()).body(response);
		}
	}

	public class BuilderError extends Builder {

		public BuilderError(_Error error) {
			response = new Response<R>(error);
		}

	}

	public class BuilderSuccess extends Builder {

		public BuilderSuccess(List<R> data) {
			response = new Response<R>(data);
		}

		public BuilderSuccess(R data) {
			response = new Response<R>(Arrays.asList(data));
		}

		public BuilderSuccess(String message) {

			if (response == null) {
				response = new Response<R>(message);
				return;
			}

			response.setMessage(message);
		}

		public BuilderSuccess withMessage(String message) {

			if (response == null) {
				throw new IllegalArgumentException("response == null");
			}

			response.setMessage(message);
			return this;
		}
	}

}
