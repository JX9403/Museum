package com.web.museum.service;


import com.web.museum.dao.UserRepository;
import com.web.museum.dto.UserResponseDTO;
import com.web.museum.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    UserResponseDTO getUserById(int id) ;

    UserResponseDTO createUser ( User user) ;

    UserResponseDTO updateUser ( int id, User user);

    void deleteUser ( int id );
}
