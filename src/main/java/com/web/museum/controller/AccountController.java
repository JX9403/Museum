package com.web.museum.controller;

import com.web.museum.entity.User;
import com.web.museum.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService ;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated  @RequestBody User user){
        ResponseEntity<?> response = accountService.registerUser(user);

        return response;
    }


}