package com.example.bek.BekRegister.controllers;

import javax.servlet.http.HttpServletRequest;
import com.example.bek.BekRegister.dto.LoginRequest;
import com.example.bek.BekRegister.entity.UserTable;
import com.example.bek.BekRegister.helpers.TokenHelper;
import com.example.bek.BekRegister.helpers.ValidateHelper;
import com.example.bek.BekRegister.models.AddBookUser;
import com.example.bek.BekRegister.models.User;
import com.example.bek.BekRegister.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @Autowired public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/create")
  public ResponseEntity create(@RequestBody User user) {
    boolean result = userService.saveUser(user);
    if (result) {
      return new ResponseEntity("user created", HttpStatus.CREATED);
    }
    return ResponseEntity.badRequest().body("bad request");
  }

  @GetMapping("/get")
  public ResponseEntity getUserById(@RequestParam Integer id) {
    UserTable userRequest = userService.getUser(id);
    if (userRequest == null || userService.getToken()==null) {
      return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(userRequest);
  }

  @PostMapping("/signIn")
  public ResponseEntity signIn(@RequestBody LoginRequest loginRequest) {
    return userService.signIn(loginRequest);
  }

  @PostMapping("/logOut")
  public ResponseEntity signOut() {
    return userService.signOut();
  }

  @PostMapping("/addBooks")
  public ResponseEntity addBookUser(@RequestBody AddBookUser request) {
    userService.addProductsToUser(request.getUserId(), request.getBookIds());
    return ResponseEntity.ok().body("User add book");
  }



  @DeleteMapping("/deleteUser")
    public ResponseEntity delete(@RequestParam Integer id){
    UserTable user = userService.getUser(id);
    if (user == null || userService.getToken()==null) {
      return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
    }
    userService.deleteUser(id);
    return ResponseEntity.ok("Account deleted");
  }

  @PutMapping("/updateuser")
  public UserTable updateUser(@RequestBody UserTable userTable) {
    return userService.updateUser(userTable);
  }


//  @DeleteMapping("/delete")
//  public String deleteById(@RequestParam Integer id) {
//    return userService.deleteUser(id)+" Employee(s) delete from the database";
//  }
//
//

//
//  @PostMapping("/login") public ResponseEntity login(@RequestBody LoginRequest request) {
//    if (ValidateHelper.validate(request.getEmail())) {
//
//      return ResponseEntity.ok(Integer.valueOf(request.getEmail().hashCode()).toString()
//              .concat(Integer.valueOf(request.getPassword().hashCode()).toString()));
//    }
//    return ResponseEntity.badRequest().body("INVALID email");
//
//  }
//
//@GetMapping("/getUser")
//public ResponseEntity getData(@RequestParam String email){
//    User user =  userService.getUser(email);
//    if(userService.getToken()==null){
//      return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
//    }
//    return ResponseEntity.ok(user);
//}
//
//  @PostMapping("/updateUser")
//  public ResponseEntity updateUser(@RequestBody User user) {
//    User userByEmail = userService.getUser(user.getEmail());
//
//    if (userByEmail == null ) {
//      return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
//    }
//
//    userService.editUser(user);
//    return ResponseEntity.ok("Updated");
//  }
//
//  @GetMapping("/getToken")
//  public ResponseEntity getToken(){
//    return  ResponseEntity.ok(userService.getTokens());
//  }
//
//@PostMapping("/deleteUser")
//  public ResponseEntity deleteUser(@RequestBody User user){
//  User userByEmail = userService.getUser(user.getEmail());
//  if (userByEmail == null || userService.getToken()==null) {
//    return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
//  }
//  userService.deleteUser(userByEmail);
//  return ResponseEntity.ok("Account deleted");
//}

//  @GetMapping("/get") public ResponseEntity check(HttpServletRequest request) {
//    String token = request.getHeader("token");
//    String email = TokenHelper.getEmailByToken(token);
//    if (userService.getUsers().containsKey(email)) {
//      User user = userService.getUsers().get(email);
//      return ResponseEntity.ok(user);
//    }
//    return ResponseEntity.badRequest().body("invalid token");
//  }

}
