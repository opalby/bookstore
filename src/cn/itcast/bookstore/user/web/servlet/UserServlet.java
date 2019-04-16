package cn.itcast.bookstore.user.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.user.domain.User;
import cn.itcast.bookstore.user.service.UserException;
import cn.itcast.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;

public class UserServlet extends BaseServlet {

	private UserService userService=new UserService();
	
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code=request.getParameter("code");
		try {
			userService.active(code);
			request.setAttribute("msg", "用户激活");
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";
	}

	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		User form=CommonUtils.toBean(request.getParameterMap(), User.class);
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
		
		Map<String,String> errors =new HashMap<String,String>();
		String username=form.getUsername();
		if(username==null||username.trim().isEmpty()){
			errors.put("name", "用户名不正确");
		}else if(username.length()<3||username.length()>10){
			errors.put("name", "用户名长度为3~10");
		}
		
		String password=form.getPassword();
		if(password==null||password.trim().isEmpty()){
			errors.put("password", "密码不正确");
		}else if(password.length()<6||password.length()>10){
			errors.put("password", "密码长度应为6~10");
		}
		
		String email=form.getEmail();
		if(email==null||email.trim().isEmpty()){
			errors.put("email", "邮箱地址不能为空");
		}else if(email.matches("//w+@//w+//.//w+")){
			errors.put("email", "邮箱地址不正确");
		}
		
		if(errors.size()>0){
			request.setAttribute("errors",errors);
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
		
		Properties por = new Properties();
		por.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		System.out.println(por.getProperty("host"));
		String host=por.getProperty("host");
		String uname=por.getProperty("uname");
		String pass=por.getProperty("password");
		String from=por.getProperty("from");
		String to=form.getEmail();
		String subject=por.getProperty("subject");
		String content=por.getProperty("content");
		content=MessageFormat.format(content, form.getCode());
		
		Session session=MailUtils.createSession(host, uname, pass);		
		Mail mail=new Mail(from,to,subject,content);
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e1) {
		}
		
		try {
			userService.regist(form);
			request.setAttribute("msg", "ע��ɹ�");
			return "f:/jsps/msg.jsp";
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
	}
	
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User form=CommonUtils.toBean(request.getParameterMap(), User.class);
		try {
			User user=userService.login(form);
			request.getSession().setAttribute("session_user", user);			
			request.getSession().setAttribute("cart", new Cart());
			return "r:/index.jsp";
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
	}
	
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		return "f:/index.jsp";
	}
	
}
