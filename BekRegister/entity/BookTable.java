package com.example.bek.BekRegister.entity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="book")
@Getter
@Setter
@Data
@NoArgsConstructor
public class BookTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bookName;
    private String bookAuthor;
    private String genre;

    public BookTable(String bookName, String bookAuthor, String genre) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.genre = genre;
    }
}
