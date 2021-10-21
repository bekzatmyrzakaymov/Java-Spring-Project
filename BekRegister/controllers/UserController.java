package com.example.bek.BekRegister.controllers;


import com.example.bek.BekRegister.dto.LoginRequest;
import com.example.bek.BekRegister.entity.UserTable;
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

  @Autowired
  public  UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/create")
  public ResponseEntity<? extends String> create(@RequestBody User user) {
    boolean result = userService.saveUser(user);
    if (result) {
      return new ResponseEntity<>("user created", HttpStatus.CREATED);
    }
    return ResponseEntity.badRequest().body("bad request");
  }

  @GetMapping("/get")
  public ResponseEntity<?> getUserById(@RequestParam Integer id) {
    UserTable userRequest = userService.getUser(id);
    if (userRequest == null || userService.getToken()==null) {
      return new ResponseEntity<Object>("User not found", HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(userRequest);
  }

  @PostMapping("/signIn")
  public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest) {
    return userService.signIn(loginRequest);
  }

  @PostMapping("/logOut")
  public ResponseEntity<?> signOut() {
    return userService.signOut();
  }

  @PostMapping("/addBooks")
  public ResponseEntity<? extends String> addBookUser(@RequestBody AddBookUser request) {
    userService.addProductsToUser(request.getUserId(), request.getBookIds());
    return ResponseEntity.ok().body("User add book");
  }


  @DeleteMapping("/deleteUser")
    public ResponseEntity<? extends String> delete(@RequestParam Integer id){

    UserTable user = userService.getUser(id);
    if (user == null || userService.getToken()==null) {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    userService.deleteUser(id);
    return ResponseEntity.ok("Account deleted");
  }


  @PutMapping("/update")
  public ResponseEntity updateUser1(@RequestBody UserTable userTable) {
    if(userService.getToken()==null){
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    userService.update(userTable);
    return ResponseEntity.ok("Account updated");
  }

  @GetMapping("/getToken")
  public ResponseEntity<? extends java.util.Map<String, String>> getToken(){
    return  ResponseEntity.ok(userService.getTokens());
  }

}
