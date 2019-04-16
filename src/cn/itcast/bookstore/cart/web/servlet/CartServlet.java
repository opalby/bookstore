package cn.itcast.bookstore.cart.web.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.cart.domain.CartItem;
import cn.itcast.servlet.BaseServlet;

public class CartServlet extends BaseServlet {
	
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		
		String bid =request.getParameter("bid");

		BookService bookService=new BookService();
		Book book=bookService.load(bid);
		
		int count=Integer.parseInt(request.getParameter("count"));
		
		CartItem cartItem=new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		
		cart.add(cartItem);
		return "f:/jsps/cart/list.jsp";
	}
	
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart) request.getSession().getAttribute("cart");		
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart) request.getSession().getAttribute("cart");		
		String bid =request.getParameter("bid");		
		cart.delete(bid);		
		return "f:/jsps/cart/list.jsp";
	}
}
