package com.example.bek.BekRegister.models;

import lombok.Data;

import java.util.List;

@Data
public class AddBookUser {
    private Integer userId;
    private List<Integer> bookIds;
}
