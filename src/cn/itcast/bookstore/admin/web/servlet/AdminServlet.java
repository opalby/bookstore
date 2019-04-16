package cn.itcast.bookstore.admin.web.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.admin.domain.Admin;
import cn.itcast.bookstore.admin.service.AdminException;
import cn.itcast.bookstore.admin.service.AdminService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class AdminServlet extends BaseServlet {
	private AdminService adminService=new AdminService();
	
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Admin form=CommonUtils.toBean(request.getParameterMap(), Admin.class);
		try {
			Admin admin=adminService.login(form);
			System.out.println(admin==null);
			request.getSession().setAttribute("session_admin", admin);
			return "r:/adminjsps/admin/index.jsp";
		} catch (AdminException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/adminjsps/login.jsp";
		}
		
		
	}
}