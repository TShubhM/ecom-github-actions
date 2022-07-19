package com.mongo.Ecom.exception;

public class MethodArgumentTypeMismatchException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String erroCode;
	private String errorMessage;

	public MethodArgumentTypeMismatchException(String erroCode, String errorMessage) {
		this.erroCode = erroCode;
		this.errorMessage = errorMessage;
	}

	public MethodArgumentTypeMismatchException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getErroCode() {
		return erroCode;
	}

	public void setErroCode(String erroCode) {
		this.erroCode = erroCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
