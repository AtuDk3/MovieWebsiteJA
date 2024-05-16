package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.RoleDTO;
import com.project.MovieWebsite.models.Role;

import java.util.List;

public interface RoleService {
    Role createRole(RoleDTO roleDTO);

    Role getRoleById(int roleId);

    List<Role> getAllRole();

    Role updateRole(int roleId, RoleDTO roleDTO);

    void deleteRole(int roleId);
}
