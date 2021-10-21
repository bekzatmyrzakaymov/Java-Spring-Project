package com.example.bek.BekRegister.reposotory;

import com.example.bek.BekRegister.entity.BookTable;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookTable,Long> {
    BookTable findByBookName(String name);
}
