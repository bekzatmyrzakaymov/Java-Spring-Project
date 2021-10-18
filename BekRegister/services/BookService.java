package com.example.bek.BekRegister.services;

import com.example.bek.BekRegister.entity.BookTable;
import com.example.bek.BekRegister.models.BookRequest;
import com.example.bek.BekRegister.reposotory.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public boolean saveBook(BookRequest bookRequest){

        BookTable book = bookRepository.findByBookName(bookRequest.getBookName());
        if(book != null){
            return false;
        }
        BookTable bookTable = new BookTable(bookRequest.getBookName(),
                bookRequest.getBookAuthor(), bookRequest.getGenre());
        bookRepository.save(bookTable);
        return true;
    }

    public BookTable getBook(Integer id){
        return bookRepository.findById(id.longValue()).orElse(null);
    }

}
