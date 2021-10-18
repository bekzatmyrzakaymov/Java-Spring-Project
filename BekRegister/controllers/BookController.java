package com.example.bek.BekRegister.controllers;

import com.example.bek.BekRegister.entity.BookTable;
import com.example.bek.BekRegister.entity.UserTable;
import com.example.bek.BekRegister.models.BookRequest;
import com.example.bek.BekRegister.models.User;
import com.example.bek.BekRegister.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/addBook")
    public ResponseEntity create(@RequestBody BookRequest bookRequest) {
        boolean result = bookService.saveBook(bookRequest);
        if (result) {
            return new ResponseEntity("You add new Book", HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body("bad request");
    }

    @GetMapping("/getBook")
    public ResponseEntity getBook(@RequestParam Integer id) {
        BookTable bookTable = bookService.getBook(id);
        if (bookTable == null) {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bookTable);
    }


}
