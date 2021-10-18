package com.example.bek.BekRegister.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
  private Integer id;
  private String email;
  private String password;
  private String firstName;
  private String secondName;
  private Integer age;

}
