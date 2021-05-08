package com.delivery.service.book.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class RigException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private ResultCodeEnum resultCode;

	public RigException(ResultCodeEnum resultCodeEnum) {
		super();
		resultCode = resultCodeEnum;
	}

	@Override
	public String toString() {
		return "RigException [errorCode=" + resultCode.getResultCode() + ", errorDesc=" + resultCode.getDescription()
				+ "]";
	}
	
	@Override
	public String getMessage() {
		return toString();
	}

}