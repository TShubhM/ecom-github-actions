package com.mongo.Ecom.exception;

import java.util.HashMap;
import java.util.Map;
//import java.util.HashMap;
//import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EcomAdviceController {

	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<Object> handleEmptyInput(EmptyInputException emptyInput) {
		return new ResponseEntity<Object>(emptyInput, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessLogic(EmptyInputException emptyInput) {
		return new ResponseEntity<Object>(emptyInput, HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Object> handleObjectAlreadyExist(ObjectNotFoundException objectAlreadyExistException) {
		return new ResponseEntity<Object>(objectAlreadyExistException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElement(NoSuchElementException noSuchElement) {
		return new ResponseEntity<Object>(noSuchElement, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchObjectExistException.class)
	public ResponseEntity<Object> handleNoSuchObject(NoSuchObjectExistException noSuchObjectExistException) {
		return new ResponseEntity<Object>(noSuchObjectExistException, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
			IllegalArgumentException illegalArgumentException) {
		Map<String, String> response = new HashMap<>();
		response.put("400", "Bad Request, Please check Your request input.");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}


//	@ExceptionHandler
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ResponseBody
//	Map<String, String> showCustomMessage(Exception exception) {
//		Map<String, String> response = new HashMap<>();
//		response.put("404", "Bad Http Request please check your request again");
//		return response;
//	}

//	@ExceptionHandler(NoSuchElementException.class)
//	public ResponseEntity<Map<String, String>> handleCategoryNotEnabled(Exception exception) {
//		Map<String, String> response = new HashMap<>();
//		response.put("404", "Category is not available with given Id. Please Check Id and request again.");
//		return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
//	}

}
