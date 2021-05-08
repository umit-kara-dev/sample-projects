package com.delivery.service.book.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.service.book.exception.ResultCodeEnum;
import com.delivery.service.book.model.request.CreateCustomerRequest;
import com.delivery.service.book.model.response.OperationResponse;
import com.delivery.service.book.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("customer/")
public class CustomerController extends RigRestController {

	@Autowired
	private CustomerService customerService;

	@Operation(summary = "Create new customer")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "If result is true that means new customer created", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OperationResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input object(s)", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@PostMapping("/createCustomer")
	public OperationResponse createCustomer(
			@Valid @RequestBody(required = true) @NotNull CreateCustomerRequest request) {
		OperationResponse response = new OperationResponse();
		try {
			customerService.createCustomer(request);
			response.setResult(ResultCodeEnum.SUCCESS);
		} catch (Exception e) {
			log.error("createCustomer error = {}", e.getMessage());
			handleException(response, e);
		} finally {
			log.info("createCustomer finished");
		}
		return response;
	}

}
