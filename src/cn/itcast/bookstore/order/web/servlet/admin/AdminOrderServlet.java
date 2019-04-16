package cn.itcast.bookstore.order.web.servlet.admin;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.order.service.OrderService;
import cn.itcast.servlet.BaseServlet;

public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService=new OrderService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("orderList", orderService.findAll());
		return "f:/adminjsps/admin/order/list.jsp";
	}
	
	public String findOrderByState(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String statein=request.getParameter("state");
		int state=Integer.parseInt(statein);
		request.setAttribute("orderList", orderService.findOrderByState(state));
		return "f:/adminjsps/admin/order/list.jsp";
	}
	
	public String send(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oid=request.getParameter("oid");
		orderService.send(oid);
		return findAll(request,response);
	}
}
