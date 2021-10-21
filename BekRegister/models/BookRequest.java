package com.example.bek.BekRegister.models;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class BookRequest {
    private Integer id;
    private String bookName;
    private String bookAuthor;
    private String genre;

}
