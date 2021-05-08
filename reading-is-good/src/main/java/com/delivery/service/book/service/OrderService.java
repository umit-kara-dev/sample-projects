package com.delivery.service.book.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.service.book.exception.ResultCodeEnum;
import com.delivery.service.book.exception.RigException;
import com.delivery.service.book.model.entity.Book;
import com.delivery.service.book.model.entity.BookOrder;
import com.delivery.service.book.model.entity.Customer;
import com.delivery.service.book.model.entity.OrderItem;
import com.delivery.service.book.model.request.Order;
import com.delivery.service.book.repository.BookOrderRepository;
import com.delivery.service.book.repository.BookRepository;
import com.delivery.service.book.util.TransactionIdGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

	@Autowired
	private BookOrderRepository orderRepository;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BookRepository bookRepository;

	/**
	 * returns the details of order by orderId
	 * 
	 * if order not found throws ORDER_NOT_FOUND exception
	 * 
	 * @param orderId
	 * @return
	 * @throws RigException if
	 */
	public BookOrder viewOrderDetails(String orderId) throws RigException {
		log.info("viewOrderDetails started for orderId = {}", orderId);
		BookOrder order = null;
		try {
			order = orderRepository.findByOrderId(orderId);
			if (order == null) {
				throw new RigException(ResultCodeEnum.ORDER_NOT_FOUND);
			}
		} catch (Exception e) {
			handleException("Error at viewOrderDetails. Error = {}", e);
		} finally {
			log.info("viewOrderDetails finished for orderId = {}", orderId);
		}
		return order;
	}

	/**
	 * creat the order and order items. update stock count and if success returns the order id
	 * 
	 *  if customer not found throws CUSTOMER_NOT_FOUND exception
	 * 
	 * if book not found throws BOOK_NOT_FOUND exception
	 * 
	 * if book stock is not enough throws NOT_ENOUGH_STOCK exception
	 * 
	 * rollback if any RigException occurs
	 * @param orders
	 * @param user
	 * @param trxId
	 * @return
	 * @throws RigException
	 */
	@Transactional(rollbackOn = RigException.class)
	public String createOrder(List<Order> orders, String user, String trxId) throws RigException {
		log.info("createOrder started for transactionId = {}", trxId);
		String orderId = null;
		try {
			LocalDateTime now = LocalDateTime.now();
			Customer customer = customerService.findByEmail(user);
			if (customer == null) {
				throw new RigException(ResultCodeEnum.CUSTOMER_NOT_FOUND);
			}
			orderId = TransactionIdGenerator.getTransactionId();
			BookOrder bookOrder = new BookOrder(user, now);
			bookOrder.setCustomer(customer);
			bookOrder.setOrderDate(now);
			bookOrder.setOrderId(orderId);
			for (Order order : orders) {
				Book book = bookRepository.findByIsbn(order.getIsbn());
				if (book == null) {
					throw new RigException(ResultCodeEnum.BOOK_NOT_FOUND);
				}
				if(order.getOrderCount() > book.getStockCount()) {
					throw new RigException(ResultCodeEnum.NOT_ENOUGH_STOCK);
				}
				//if enough stock exists, decrease stock count and save it
				book.decreaseStockCount(order.getOrderCount());
				book.setUpdateDate(now);
				book.setUpdateUser(orderId);
				book = bookRepository.save(book);
				
				OrderItem orderItem = new OrderItem(user, now);
				orderItem.setBook(book);
				orderItem.setAmount(book.getPrice().multiply(new BigDecimal(order.getOrderCount())));
				orderItem.setOrderCount(order.getOrderCount());
				orderItem.setOrder(bookOrder);
				bookOrder.getOrderItems().add(orderItem);
			}
			orderRepository.save(bookOrder);
		} catch (Exception e) {
			handleException("Error at createOrder. Error = {}", e);
		} finally {
			log.info("createOrder finished for transactionId = {}", trxId);
		}
		return orderId;
	}

	/**
	 * list orders of the customer by using email/unique key
	 * 
	 * if customer not found throws CUSTOMER_NOT_FOUND exception
	 * @param email
	 * @return
	 * @throws RigException
	 */
	public List<BookOrder> listOrders(String email) throws RigException {
		List<BookOrder> list = null;
		log.info("listOrders started for customer = {}", email);
		try {
			Customer customer = customerService.findByEmail(email);
			if (customer == null) {
				throw new RigException(ResultCodeEnum.CUSTOMER_NOT_FOUND);
			}
			list = orderRepository.findByCustomer(email);
		} catch (Exception e) {
			handleException("Error at listOrders. Error = {}", e);
		} finally {
			log.info("listOrders finished for customer = {}", email);
		}
		return list;
	}

	/**
	 * if any Exception other than RigException occurs throw a SYSTEM_ERROR
	 * @param message
	 * @param e
	 * @throws RigException
	 */
	private void handleException(String message, Exception e) throws RigException {
		if (e instanceof RigException) {
			log.error(message, e.toString());
			throw (RigException) e;
		} else {
			log.error(message + " Error = {}", e);
			throw new RigException(ResultCodeEnum.SYSTEM_ERROR);
		}
	}
}
