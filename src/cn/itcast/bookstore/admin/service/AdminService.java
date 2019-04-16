package cn.itcast.bookstore.admin.service;

import cn.itcast.bookstore.admin.domain.Admin;

public class AdminService {

	public Admin login(Admin admin) throws AdminException {
		System.out.println(admin.getAdminname());
		if(!admin.getAdminname().equals("admin")){
			throw new AdminException("用户名不存在");
		}
		
		if(!admin.getPassword().equals("123")){
			throw new AdminException("密码错误");
		}
		
		return admin;
	}

}
