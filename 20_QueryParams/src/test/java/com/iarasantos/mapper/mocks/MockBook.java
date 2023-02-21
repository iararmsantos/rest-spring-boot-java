package com.iarasantos.mapper.mocks;

import com.iarasantos.data.vo.v1.BookVO;
import com.iarasantos.model.Book;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookVO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("Author Test" + number);
        book.setTitle("Title Test" + number);
        book.setPrice(26.66);
        book.setLaunchDate(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());

        return book;
    }

    public BookVO mockVO(Integer number) {
        BookVO book = new BookVO();
        book.setKey(number.longValue());
        book.setAuthor("Author Test" + number);
        book.setTitle("Title Test" + number);
        book.setPrice(26.66);
        book.setLaunchDate(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());
        return book;
    }
}