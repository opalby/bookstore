package cn.itcast.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.bookstore.order.dao.OrderDao;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	private OrderDao orderDao=new OrderDao();
	
	public void pay(String oid){
		int state=orderDao.getStateByOid(oid);
		
		if(state==1){
			orderDao.updateState(oid, 2);
		}
	}
	
	public void add(Order order){
		try{
			JdbcUtils.beginTransaction();
			
			orderDao.addOrder(order);
			orderDao.addOrderItemList(order.getOrderItemList());
			
			JdbcUtils.commitTransaction();
		}catch(Exception e){
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
			}
			throw new RuntimeException(e);
		}
		
	}

	public List<Order> myOrders(String uid) {
		return orderDao.findByUid(uid);
	}
	
	public void confirm(String oid) throws OrderException{
		int state =orderDao.getStateByOid(oid);
		if(state!=3){
			throw new OrderException("∂©µ•»∑»œ ß∞‹");
		}
		
		orderDao.updateState(oid, 4);
	}

	public Order load(String oid) {
		return orderDao.load(oid);
	}

	public List<Order> findAll() {
		return orderDao.findAll();
	}

	public List<Order> findOrderByState(int state) {
		return orderDao.findOrderByState(state);
	}

	public void send(String oid) {
		int state =orderDao.getStateByOid(oid);
		if(state==2){
			orderDao.updateState(oid, 3);
		}		
	}
}
