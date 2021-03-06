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
        productIds.forEach(id -> bookRepository.findById(id.longValue()).ifPresent(bookTables::add));
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

    public boolean update(UserTable userTable){
        UserTable byEmail = userRepository.findByEmail(userTable.getEmail());

        if(byEmail == null) {
            return false;
        }

        byEmail.setSecondName(userTable.getSecondName());
        byEmail.setFirstName(userTable.getFirstName());
        byEmail.setPassword(userTable.getPassword());
        userRepository.save(byEmail);

        return true;
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
