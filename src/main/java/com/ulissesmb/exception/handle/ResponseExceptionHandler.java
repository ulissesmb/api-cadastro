package com.ulissesmb.exception.handle;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ulissesmb.exception.BusinessException;
import com.ulissesmb.exception.HeaderException;
import com.ulissesmb.exception.NotFoundException;
import com.ulissesmb.exception.ValidationException;
import com.ulissesmb.response.Response;
import com.ulissesmb.response.ResponseBuilder;
import com.ulissesmb.response._Error;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseExceptionHandler.class);

	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<Response> notFoundException(NotFoundException e) {
		return new ResponseBuilder().withError(getError("999999", e.getMessage())).withHttp(HttpStatus.NOT_FOUND).build();
	}
	
	@ExceptionHandler(value = UsernameNotFoundException.class)
	public ResponseEntity<Response> userNameNotFoundException(UsernameNotFoundException e) {
		return new ResponseBuilder().withError(getError("999999",e.getMessage())).withHttp(HttpStatus.NOT_FOUND).build();
	}

	@ExceptionHandler(value = HeaderException.class)
	public ResponseEntity<Response> headerException(HeaderException e) {
		return new ResponseBuilder().withError(getError(e)).withHttp(HttpStatus.BAD_REQUEST).build();
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<Response> illegalArgumentException(IllegalArgumentException e) {
		_Error error = getError("ILA" + e.hashCode(), e.getMessage());
		return new ResponseBuilder().withError(error).withHttp(HttpStatus.BAD_REQUEST).build();
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<Response> ConstraintViolationException(ConstraintViolationException e) {
		_Error error = getError("CV" + e.hashCode(), e.getMessage());
		return new ResponseBuilder().withError(error).withHttp(HttpStatus.BAD_REQUEST).build();
	}

	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<Response> validationException(ValidationException e) {
		_Error error = getError("V" + e.hashCode(), e.getMessage());
		return new ResponseBuilder().withError(error).withHttp(HttpStatus.BAD_REQUEST).build();
	}

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<Response> runtimeException(RuntimeException e) {
		_Error error = getError("" + e.hashCode(), e.getMessage());
		return new ResponseBuilder().withError(error).withHttp(HttpStatus.BAD_REQUEST).build();
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		final ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
		String errorMessage = objectError.getDefaultMessage();
		final _Error error = getError("MANV" + errorMessage.hashCode(), errorMessage);
		return new ResponseBuilder().withError(error).withHttp(HttpStatus.BAD_REQUEST).build();

	}

	private _Error getError(BusinessException e) {
		return getError(e.getCode(), e.getMessage());
	}

	private _Error getError(String code, String message) {
		LOGGER.info("Responding not founded exception!");
		LOGGER.info("Parsing exception to error to create a friendly response");
		LOGGER.info(message);
		return new _Error(code, message);
	}
}
