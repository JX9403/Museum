package com.web.museum.controller;

import com.web.museum.dto.PagedResponse;
import com.web.museum.dto.UserResponseDTO;
import com.web.museum.entity.User;
import com.web.museum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<PagedResponse<UserResponseDTO>> getAllUsers (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5" )int size){
       
        Pageable pageable = PageRequest.of(page, size);
        
        Page<UserResponseDTO> users = userService.getAllUsers(pageable);
        
        PagedResponse<UserResponseDTO> response = new PagedResponse<>(
                users.getContent(),
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages()
        );

        return ResponseEntity.ok(response);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id){
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser ( @RequestBody User user){
        UserResponseDTO userResponseDTO = userService.createUser(user);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser ( @PathVariable int id, @RequestBody User user ){
        UserResponseDTO userResponseDTO = userService.updateUser(id, user);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser ( @PathVariable int id ){
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete successfully!");
    }
}


