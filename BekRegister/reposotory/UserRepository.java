package com.example.bek.BekRegister.reposotory;

import com.example.bek.BekRegister.entity.UserTable;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserTable, Long> {
    UserTable findByEmail(String email);
}
