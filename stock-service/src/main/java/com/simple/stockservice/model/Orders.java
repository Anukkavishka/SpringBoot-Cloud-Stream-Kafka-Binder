package com.simple.stockservice.model;

public class Orders {
	
	private String orderId;
	private String proName;
	private int qty;
	
	
	public Orders(String orderId, String proName, int qty) {
		super();
		this.orderId = orderId;
		this.proName = proName;
		this.qty = qty;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getProName() {
		return proName;
	}


	public void setProName(String proName) {
		this.proName = proName;
	}


	public int getQty() {
		return qty;
	}


	public void setQty(int qty) {
		this.qty = qty;
	}


	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
