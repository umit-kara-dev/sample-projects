package com.delivery.service.book.controller;

import com.delivery.service.book.exception.ResultCodeEnum;
import com.delivery.service.book.exception.RigException;
import com.delivery.service.book.model.response.OperationResponse;

public abstract class RigRestController {

	public void handleException(OperationResponse response, Exception e) {
		if (e instanceof RigException) {
			response.setResult(((RigException) e).getResultCode());
		} else {
			response.setResult(ResultCodeEnum.SYSTEM_ERROR);
		}
	}
}
