package com.simple.orderservice.model;

public class OrderEvent {
	
	private Orders order;
	private OrderEventType status;
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public OrderEventType getStatus() {
		return status;
	}
	public void setStatus(OrderEventType status) {
		this.status = status;
	}
	public OrderEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderEvent(Orders order, OrderEventType status) {
		super();
		this.order = order;
		this.status = status;
	}
	
	
	

}
