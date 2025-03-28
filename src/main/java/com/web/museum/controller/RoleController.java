package com.web.museum.controller;

import com.web.museum.dto.PagedResponse;
import com.web.museum.dto.RoleResponseDTO;
import com.web.museum.entity.Role;
import com.web.museum.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    public RoleService roleService ;
    
    @GetMapping
    public ResponseEntity<PagedResponse<RoleResponseDTO>> getAllRoles (@RequestParam ( defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size ){
        Pageable pageable = PageRequest.of(page, size);
        
        Page<RoleResponseDTO> roles = roleService.getAllRoles(pageable);

        PagedResponse<RoleResponseDTO> response = new PagedResponse<>(
                roles.getContent(),
                roles.getNumber(),
                roles.getSize(),
                roles.getTotalElements(),
                roles.getTotalPages()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable int id){
        RoleResponseDTO roleResponseDTO = roleService.getRoleById(id);
        return ResponseEntity.ok(roleResponseDTO);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody Role role){
        RoleResponseDTO roleResponseDTO = roleService.createRole(role);
        return ResponseEntity.ok(roleResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable int id, @RequestBody Role reqRole){
        RoleResponseDTO roleResponseDTO = roleService.updateRole(id, reqRole);
        return ResponseEntity.ok(roleResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Delete Role successfully");
    }
}

