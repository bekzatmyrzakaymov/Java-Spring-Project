package com.example.bek.BekRegister.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "register")
@Getter
@Setter
@NoArgsConstructor
public class UserTable {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
    @Column(columnDefinition = "TEXT")
    private String firstName;
    private String secondName;
    private String email;
    private String password;

    @ManyToMany
    private List<BookTable> bookTables;

    public UserTable(String firstName, String secondName, String email, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
