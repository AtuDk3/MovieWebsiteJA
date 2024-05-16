package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.RoleDTO;
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
    public Role createRole(RoleDTO roleDTO) {
        Role newRole = Role.builder().name(roleDTO.getName()).build();
        return roleRepository.save(newRole);
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
    public Role updateRole(int roleId, RoleDTO roleDTO) {
        Role existsRole = getRoleById(roleId);
        existsRole.setName(roleDTO.getName());
        roleRepository.save(existsRole);
        return existsRole;
    }

    @Override
    public void deleteRole(int roleId) {
        roleRepository.deleteById(roleId);
    }
}
