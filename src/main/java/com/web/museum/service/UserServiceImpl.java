package com.web.museum.service;


import com.web.museum.dao.RoleRepository;
import com.web.museum.dao.UserRepository;
import com.web.museum.dto.RoleResponseDTO;
import com.web.museum.dto.UserResponseDTO;
import com.web.museum.entity.Role;
import com.web.museum.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(this::convertToDTO);
    }

    @Override
    public UserResponseDTO getUserById(int id) {
        Optional<User> userOptional = userRepository.findById(id) ;
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return convertToDTO(userOptional.get());
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(User reqUser) {

        if (reqUser.getFullname() == null || reqUser.getFullname().trim().isEmpty()) {
            throw new RuntimeException("Fullname is required");
        }
        if (reqUser.getPassword() == null || reqUser.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }
        if (reqUser.getEmail() == null || reqUser.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (userRepository.existsByEmail(reqUser.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        User user = new User();
        user.setFullname(reqUser.getFullname());
        user.setEmail(reqUser.getEmail());
        user.setPassword(reqUser.getPassword());
//        user.setPassword(passwordEncoder.encode(reqUser.getPassword()));

        List<Role> validRoles = new ArrayList<>();
        if (reqUser.getListRoles() != null && !reqUser.getListRoles().isEmpty()) {
            for (Role role : reqUser.getListRoles()) {
                Optional<Role> existingRole = roleRepository.findById(role.getId());
                if (existingRole.isPresent()) {
                    validRoles.add(existingRole.get());
                } else {
                    throw new RuntimeException("Role with ID " + role.getId() + " does not exist");
                }
            }
        } else {
            throw new RuntimeException("At least one role is required");
        }
        user.setListRoles(validRoles);

        // Lưu user vào database
        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(int userId, User reqUser) {
        Optional<User> existingUserOpt = userRepository.findById(userId);
        if (!existingUserOpt.isPresent()) {
            throw new RuntimeException("User with ID " + userId + " does not exist");
        }

        User existingUser = existingUserOpt.get();
        existingUser.setFullname(reqUser.getFullname());
        existingUser.setPassword(reqUser.getPassword());
        existingUser.setEmail(reqUser.getEmail());

        // Kiểm tra và cập nhật danh sách vai trò nếu có
        if (reqUser.getListRoles() != null && !reqUser.getListRoles().isEmpty()) {
            List<Role> validRoles = new ArrayList<>();
            for (Role role : reqUser.getListRoles()) {
                Optional<Role> existingRole = roleRepository.findById(role.getId());
                if (existingRole.isPresent()) {
                    validRoles.add(existingRole.get());
                } else {
                    throw new RuntimeException("Role with ID " + role.getId() + " does not exist");
                }
            }
            existingUser.setListRoles(validRoles);
        } else {
            existingUser.setListRoles(new ArrayList<>()); // Khởi tạo danh sách rỗng nếu không có vai trò
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    @Override
    public void deleteUser(int id) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (!existingUserOpt.isPresent()) {
            throw new RuntimeException("User with ID " + id + " does not exist");
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFullname(user.getFullname());
        userResponseDTO.setEmail(user.getEmail());

        List<RoleResponseDTO> roleResponseDTOs = user.getListRoles().stream()
                .map(role -> {
                    RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
                    roleResponseDTO.setId(role.getId());
                    roleResponseDTO.setName(role.getName());
                    return roleResponseDTO;
                })
                .collect(Collectors.toList());

        userResponseDTO.setRoles(roleResponseDTOs);
        return userResponseDTO;
    }
}
