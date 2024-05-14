package com.project.MovieWebsite.services;

import com.project.MovieWebsite.models.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);

    Role getRoleById(int roleId);

    List<Role> getAllRole();

    Role updateRole(int roleId, Role role);

    void deleteRole(int roleId);
}
