package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.models.Order;
import com.project.MovieWebsite.models.Role;
import com.project.MovieWebsite.repositories.RoleRepository;
import com.project.MovieWebsite.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(int roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found!"));
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role updateRole(int roleId, Role role) {
        Role existsRole = getRoleById(roleId);
        existsRole.setName(role.getName());
        return existsRole;
    }

    @Override
    public void deleteRole(int roleId) {
        roleRepository.deleteById(roleId);
    }
}
