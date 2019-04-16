package cn.itcast.bookstore.user.service;

import cn.itcast.bookstore.user.dao.UserDao;
import cn.itcast.bookstore.user.domain.User;

public class UserService {
	private UserDao userDao=new UserDao();
	
	public void regist(User form) throws UserException {
		User us= userDao.findByUsername(form.getUsername());
		if(us!=null) {
			throw new UserException("用户已存在");
		}
		
		us= userDao.findByEmail(form.getEmail());
		if(us!=null) {
			throw new UserException("邮箱已存在");
		}
		
		userDao.add(form);
	}
	
	public void active(String code) throws UserException{
		User user=userDao.findByCode(code);
		if(user==null){
			throw new UserException("用户不存在");
		}
		
		if(user.isState()==true){
			throw new UserException("用户已激活");
		}
		
		userDao.updateState(user.getUid(),true);
	}
	
	public User login(User form) throws UserException{
		User user=userDao.findByUsername(form.getUsername());
		if(user==null){
			throw new UserException("用户不存在");
		}
		
		if(!user.getPassword().equals(form.getPassword()))
			throw new UserException("密码错误");
		
		if(user.isState()==false)
			throw new UserException("用户未激活");
		
		return user;
	}
}
