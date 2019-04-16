package cn.itcast.bookstore.book.service;

import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.book.domain.Book;

public class BookService {
	private BookDao bookDao=new BookDao();
	public List<Book> findAll() {
		return bookDao.findAll();		
	}
	public List<Book> findByCategory(String cid) {
		return bookDao.findByCategory(cid);
	}
	public Book load(String bid) {
		return bookDao.getByBid(bid);
	}
	public void add(Book book) {
		bookDao.add(book);
	}
	public void delete(String bid){
		bookDao.delete(bid);
	}
	public void edit(Book book) {
		bookDao.edit(book);		
	}
	
}
