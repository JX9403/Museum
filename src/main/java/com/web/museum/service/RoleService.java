package com.web.museum.service;

import com.web.museum.dto.RoleResponseDTO;
import com.web.museum.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<RoleResponseDTO> getAllRoles(Pageable pageable);
    RoleResponseDTO getRoleById(int id);
    RoleResponseDTO createRole(Role role);
    RoleResponseDTO updateRole(int id, Role role);
    void deleteRole( int id);
}
