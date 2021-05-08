package com.delivery.service.book.controller;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.service.book.exception.ResultCodeEnum;
import com.delivery.service.book.model.entity.BookOrder;
import com.delivery.service.book.model.request.CreateOrderRequest;
import com.delivery.service.book.model.request.OperationRequest;
import com.delivery.service.book.model.request.OrderDetailRequest;
import com.delivery.service.book.model.response.BookInfo;
import com.delivery.service.book.model.response.CreateOrderResponse;
import com.delivery.service.book.model.response.OrderDetailResponse;
import com.delivery.service.book.model.response.OrderInfo;
import com.delivery.service.book.model.response.OrderItemInfo;
import com.delivery.service.book.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("order/")
public class OrderController extends RigRestController {

	public static final String DATE_FORMAT_DATETIME_NO_DELIMETER_YEAR_FIRST = "yyyyMMddHHmmss";

	@Autowired
	private OrderService orderService;

	@Operation(summary = "View order details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "If result is true that means there is no exception", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetailResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input object(s)", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@PostMapping("/viewOrderDetails")
	public OrderDetailResponse viewOrderDetails(
			@Valid @RequestBody(required = true) @NotNull OrderDetailRequest request) {
		OrderDetailResponse response = new OrderDetailResponse();
		try {
			BookOrder order = orderService.viewOrderDetails(request.getOrderId());
			if (order != null) {
				OrderInfo orderInfo = convertToResponseObject(order);
				response.setResult(ResultCodeEnum.SUCCESS);
				response.getOrderList().add(orderInfo);
			} else {
				response.setResult(ResultCodeEnum.ORDER_NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("viewOrderDetails error = {}", e.getMessage());
			handleException(response, e);
		} finally {
			log.info("viewOrderDetails finished");
		}
		return response;
	}

	@Operation(summary = "Create new order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "If result is true that means order created", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CreateOrderResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input object(s)", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@PostMapping("/createOrder")
	public CreateOrderResponse createOrder(@Valid @RequestBody(required = true) @NotNull CreateOrderRequest request) {
		CreateOrderResponse response = new CreateOrderResponse();
		try {
			String user = getUserName();
			String orderId = orderService.createOrder(request.getItemList(), user, request.getTransactionId());
			response.setOrderId(orderId);
			response.setResult(ResultCodeEnum.SUCCESS);
		} catch (Exception e) {
			log.error("createOrder error = {}", e.getMessage());
			handleException(response, e);
		} finally {
			log.info("createOrder finished");
		}
		return response;
	}

	@Operation(summary = "List orders")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "If result is true that means there is no exception", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetailResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input object(s)", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@PostMapping("/listOrders")
	public OrderDetailResponse listOrders(@Valid @RequestBody(required = true) @NotNull OperationRequest request) {
		OrderDetailResponse response = new OrderDetailResponse();
		try {
			List<BookOrder> orderList = orderService.listOrders(getUserName());
			if (orderList != null && !orderList.isEmpty()) {
				List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
				orderList.forEach(bookOrder -> {
					orderInfoList.add(convertToResponseObject(bookOrder));
				});
				response.setOrderList(orderInfoList);
				response.setResult(ResultCodeEnum.SUCCESS);
			} else {
				response.setResult(ResultCodeEnum.ORDER_NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("listOrders error = {} ", e.getMessage());
			handleException(response, e);
		} finally {
			log.info("listOrders finished");
		}
		return response;
	}

	/**
	 * return the username from security context. it would be the username of basic
	 * authentication or loggedin user.
	 * 
	 * @return
	 */
	private String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	/**
	 * convert entity object to response object. it uses just the needed attributes
	 * 
	 * @param order
	 * @return
	 */
	private OrderInfo convertToResponseObject(BookOrder order) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderDate(
				order.getOrderDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_DATETIME_NO_DELIMETER_YEAR_FIRST)));
		orderInfo.setOrderId(order.getOrderId());
		orderInfo.setStatus(order.getStatus());
		orderInfo.setTotalAmount(BigDecimal.ZERO);
		if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
			order.getOrderItems().stream().forEach(orderItem -> {
				OrderItemInfo orderItemInfo = new OrderItemInfo();
				BookInfo bookInfo = new BookInfo();
				bookInfo.setAuthor(orderItem.getBook().getAuthor());
				bookInfo.setName(orderItem.getBook().getName());
				orderItemInfo.setBookInfo(bookInfo);
				orderItemInfo.setOrderCount(orderItem.getOrderCount());
				orderItemInfo.setAmount(orderItem.getAmount());
				// calculate total amount by adding all order amount
				orderInfo.setTotalAmount(orderInfo.getTotalAmount().add(orderItemInfo.getAmount()));
				orderInfo.getOrderItems().add(orderItemInfo);
			});
		}
		return orderInfo;
	}
}
