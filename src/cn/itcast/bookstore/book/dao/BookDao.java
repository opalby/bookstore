package cn.itcast.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr=new TxQueryRunner();

	public List<Book> findAll() {		
		try {
			String sql="select * from book where del=false";
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Book> findByCategory(String cid) {		
		try {
			String sql="select * from book where cid=? and del=false";
			return qr.query(sql, new BeanListHandler<Book>(Book.class),cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Book getByBid(String bid) {		
		try {
			String sql="select * from book where bid=?";
			Map<String,Object> map=qr.query(sql, new MapHandler(),bid);
			Book book=CommonUtils.toBean(map, Book.class);
			Category category=CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int getCountByCid(String cid) {
		try {
			String sql="select count(*) from book where cid=?";
			Number cnt=(Number)qr.query(sql, new ScalarHandler(),cid);
			return cnt.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Book book) {
		try {
			String sql="insert into book values(?,?,?,?,?,?)";
			Object[] param=new Object[]{book.getBid(),book.getBname(),book.getPrice(),
					book.getAuthor(),book.getImage(),book.getCategory().getCid()};
			qr.update(sql,param);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(String bid){
		try {
			String sql="update book set del=true where bid=?";
			qr.update(sql,bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void edit(Book book) {
		try {
			String sql="update book set bname=?,price=?,author=?,image=?,cid=? where bid=?";
			Object[] param=new Object[]{book.getBname(),book.getPrice(),
					book.getAuthor(),book.getImage(),book.getCategory().getCid(),book.getBid()};
			qr.update(sql,param);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
