
package com.thelibrarian.data.service;

import com.thelibrarian.data.entity.BookEntity;
import com.thelibrarian.data.repository.IBookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceBBDD implements IBookService {

    @Autowired
    private IBookDao bookDao;


    public List <BookEntity> findAll(){

        return bookDao.findAll();
    }


    public BookEntity save(BookEntity book) {

        return bookDao.save(book);
    }

    public BookEntity findById(Integer id) {

        return bookDao.findById(id).orElse(null);
    }

    public void delete(Integer id) {

        bookDao.deleteById(id);

    }
    public BookEntity Update (BookEntity book, Integer id){

        if(bookDao.existsById(id)){

            book.setId_book(id);

            return bookDao.save(book);
        }
        return null;
    }

    @Override
    public BookEntity findByIsbn(String isbn) {

        return bookDao.findByIsbn(isbn);

    }
}

