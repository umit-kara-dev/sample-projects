package com.delivery.service.book.model.response;

import java.io.Serializable;
import java.util.List;

import com.delivery.service.book.exception.ResultCodeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class OperationResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String resultCode;
	@Getter
	@Setter
	private String message;
	@Getter
	@Setter
	private boolean success;
	@Getter
	@Setter
	private List<String> errors;

	public void setResult(ResultCodeEnum resultCodeEnum) {
		this.resultCode = resultCodeEnum.getResultCode();
		this.message = resultCodeEnum.getDescription();
		this.setSuccess(resultCodeEnum.isSuccess());
	}

	public OperationResponse(ResultCodeEnum resultCodeEnum) {
		super();
		setResult(resultCodeEnum);
	}

	public OperationResponse(ResultCodeEnum resultCodeEnum, String message) {
		super();
		setResult(resultCodeEnum);
		setMessage(message);
	}

}
