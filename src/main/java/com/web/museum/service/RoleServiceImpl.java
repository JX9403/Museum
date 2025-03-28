package com.web.museum.service;

import com.web.museum.dao.RoleRepository;
import com.web.museum.dto.RoleResponseDTO;
import com.web.museum.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleResponseDTO convertToDTO(Role role) {
        if (role == null) {
            return null;
        }
        RoleResponseDTO roleDTO = new RoleResponseDTO();
        roleDTO.setId(role.getId()); // Assuming you want to convert Long to int
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    @Override
    public Page<RoleResponseDTO> getAllRoles(Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(pageable);

        return roles.map(this::convertToDTO);
    }

    @Override
    public RoleResponseDTO getRoleById(int id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if(roleOptional.isEmpty()){
            throw new RuntimeException("Role not found");
        }
        return convertToDTO(roleOptional.get());
    }

    @Override
    public RoleResponseDTO createRole(Role reqRole) {

        if (reqRole.getName() == null || reqRole.getName().isEmpty()) {
            throw new RuntimeException("Name is required");
        }
        if (reqRole.getDescription() == null || reqRole.getDescription().isEmpty()) {
            throw new RuntimeException("Description is required");
        }

        Role role = new Role();
        role.setName(reqRole.getName());
        role.setDescription(reqRole.getDescription());

        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    @Override
    public RoleResponseDTO updateRole(int id, Role reqRole) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if(roleOptional.isEmpty()){
            throw new RuntimeException("Role not found");
        }
        Role role = roleOptional.get();

        if (reqRole.getName() == null || reqRole.getName().isEmpty()) {
            throw new RuntimeException("Name is required");
        }
        if (reqRole.getDescription() == null || reqRole.getDescription().isEmpty()) {
            throw new RuntimeException("Description is required");
        }

        role.setName(reqRole.getName());
        role.setDescription(reqRole.getDescription());

        roleRepository.save(role);
        return convertToDTO(role);
    }

    @Override
    public void deleteRole(int id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if(roleOptional.isEmpty()){
            throw new RuntimeException("Role not found");
        }
        roleRepository.deleteById(id);
    }
}
