package com.example.bek.BekRegister.services;

import com.example.bek.BekRegister.dto.LoginRequest;
import com.example.bek.BekRegister.entity.BookTable;
import com.example.bek.BekRegister.entity.UserTable;
import com.example.bek.BekRegister.helpers.TokenHelper;
import com.example.bek.BekRegister.helpers.ValidateHelper;
import com.example.bek.BekRegister.models.User;
import com.example.bek.BekRegister.reposotory.BookRepository;
import com.example.bek.BekRegister.reposotory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {


    private String token = null;
    private final Map<String, String> tokens = new HashMap<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public boolean saveUser(User userRequest) {

        if(!ValidateHelper.validate(userRequest.getEmail())) {
            return false;
        }

        UserTable byEmail = userRepository.findByEmail(userRequest.getEmail());

        if(byEmail != null) {
            return false;
        }

        UserTable user = new UserTable(userRequest.getFirstName(), userRequest.getSecondName(),
                userRequest.getEmail(),userRequest.getPassword());

        userRepository.save(user);
        tokens.put(TokenHelper.getToken(user.getEmail()),user.getPassword());

        return true;
    }

    public boolean addProductsToUser(Integer userId, List<Integer> productIds) {

        Optional<UserTable> userOptional = userRepository.findById(userId.longValue());
        UserTable user = userOptional.orElse(null);
        if(user == null) {
            return false;
        }

        List<BookTable> bookTables = new ArrayList<>();
        productIds.forEach(id -> bookRepository.findById(id.longValue()).ifPresent(p -> bookTables.add(p)));
        user.setBookTables(bookTables);
        userRepository.save(user);
        return true;
    }

    public UserTable getUser(Integer id) {
        return userRepository.findById(id.longValue()).orElse(null);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById((long)id);
    }

    public UserTable updateUser(UserTable userTable) {
        UserTable oldUser=null;
        Optional<UserTable> optionaluser=userRepository.findById(userTable.getId());
        if(optionaluser == null) {
            oldUser=optionaluser.get();
            oldUser.setFirstName(userTable.getFirstName());
            oldUser.setEmail(userTable.getEmail());
            userRepository.save(oldUser);
        }else {
            System.out.println("Error");
        }
        return oldUser;
    }

      public ResponseEntity signIn(LoginRequest loginRequest){
      if(getToken() == null){
          if(userRepository.findByEmail(loginRequest.getEmail()).getEmail().equals(loginRequest.getEmail())){
              if(userRepository.findByEmail(loginRequest.getEmail()).getPassword().equals(loginRequest.getPassword())){
                String tk = TokenHelper.getToken(loginRequest.getEmail());
                setToken(tk);
                return ResponseEntity.ok("You are signed successfully!!!" +
                        "\n" +
                        ""+
                        "Your Token: "+ TokenHelper.getToken(loginRequest.getEmail()));
              }
              return ResponseEntity.badRequest().body("Password is wrong!!!");
          }
          return ResponseEntity.badRequest().body("This user not found!!!");
      }
      return ResponseEntity.badRequest().body("Please firstly sign out!!!");
  }

        public ResponseEntity signOut(){
        if(getToken() != null){
            setToken(null);
            return ResponseEntity.ok("You are signed out!!!");
        }
        return ResponseEntity.badRequest().body("You are already signed out man!!!");
    }



//      public ResponseEntity signIn(LoginRequest loginRequest){
//        if(!getSigned()){
//
//          if(userRepository.findByEmail(loginRequest.getEmail()).getEmail().equals(loginRequest.getEmail())){
//              if(userRepository.findByEmail(loginRequest.getEmail()).getPassword().equals(loginRequest.getPassword())){
//                  setSigned(true);
//                  return ResponseEntity.ok("You are signed successfully!!!");
//              }
//            return ResponseEntity.badRequest().body("Password is wrong!!!");
//
//          }
//          return ResponseEntity.badRequest().body("This user not found!!!");
//
//        }
//        return ResponseEntity.badRequest().body("Please firstly sign out!!!");
//  }

//      public ResponseEntity signOut(){
//            if(getSigned()){
//              setSigned(false);
//              return ResponseEntity.ok("You are signed out!!!");
//            }
//            return ResponseEntity.badRequest().body("You already signed out!!!");
//        }


//      private final Map<String, User> users = new HashMap<>();


//    public boolean saveUser(User user) {
//        if(!ValidateHelper.validatePassword(user.getPassword())){
//          return false;
//        }
//
//        if (users.containsKey(user.getEmail())) {
//          return false;
//        }
//        users.put(user.getEmail(), user);
//        tokens.put(TokenHelper.getToken(user.getEmail()),user.getPassword());
//    return true;
//  }
//
//  public ResponseEntity signIn(LoginRequest loginRequest){
//      if(getToken() == null){
//          if(users.containsKey(loginRequest.getEmail())){
//              if(users.get(loginRequest.getEmail()).getPassword().equals(loginRequest.getPassword())){
//                String tk = TokenHelper.getToken(loginRequest.getEmail());
//                setToken(tk);
//                return ResponseEntity.ok("You are signed successfully!!!" +
//                        "\n" +
//                        ""+
//                        "Your Token: "+ TokenHelper.getToken(loginRequest.getEmail()));
//              }
//              return ResponseEntity.badRequest().body("Password is wrong!!!");
//          }
//          return ResponseEntity.badRequest().body("This user not found!!!");
//      }
//      return ResponseEntity.badRequest().body("Please firstly sign out!!!");
//  }
//

//
//  public User getUser(String email) {
//    return users.get(email);
//  }
//
//  public void editUser(User user) {
//    users.put(user.getEmail(), user);
//  }
//
//  public void deleteUser(User user){
//    users.remove(user.getEmail(),user);
//  }
//
//  public Map<String, User> getUsers() {
//    return users;
//  }
//
  public Map<String, String> getTokens() {
    return tokens;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
