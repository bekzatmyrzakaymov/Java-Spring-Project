package com.example.bek.BekRegister.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateHelper {
  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  public static boolean validateName(String name) {
    String regx = "^(?=.*?[A-Z])(?=.*?[a-z]).{4,}$";
    Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(name);
    return matcher.find();
  }

  public static boolean validatePassword(String pass) {
    String regx = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(pass);
    return matcher.find();
  }


  public static boolean validate(String emailStr) {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
    return matcher.find();
  }
}
