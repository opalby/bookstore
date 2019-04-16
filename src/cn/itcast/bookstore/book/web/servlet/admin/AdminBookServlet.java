package cn.itcast.bookstore.book.web.servlet.admin;



import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class AdminBookServlet extends BaseServlet {
	private BookService bookService=new BookService();
	private CategoryService categoryService=new CategoryService();
	
	public String addPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Category> categoryList=categoryService.findAll();
		System.out.println(categoryList.get(0).getCid());
		request.setAttribute("categoryList", categoryList);
		return "f:/adminjsps/admin/book/add.jsp";
	}
	
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("bookList", bookService.findAll());
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	public String load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String bid=request.getParameter("bid");
		Book book=bookService.load(bid);
		request.setAttribute("book",book);
		request.setAttribute("categoryList", categoryService.findAll());
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid=request.getParameter("bid");
		bookService.delete(bid);
		return findAll(request,response);
	}
	
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Book book=CommonUtils.toBean(request.getParameterMap(), Book.class);
		Category category=CommonUtils.toBean(request.getParameterMap(), Category.class);
		book.setCategory(category);
		
		bookService.edit(book);
		return findAll(request,response);
	}
}
