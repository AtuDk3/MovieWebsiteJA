package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.RoleDTO;
import com.project.MovieWebsite.models.Role;
import com.project.MovieWebsite.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor

public class RoleController {
    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<List<Role>> getAllGenres(
    ) {
        List<Role> roles = roleService.getAllRole();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("")
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleDTO roleDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        roleService.createRole(roleDTO);
        return ResponseEntity.ok("Create role successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRoleById(@PathVariable int id, @Valid @RequestBody RoleDTO roleDTO) {
        roleService.updateRole(id, roleDTO);
        return ResponseEntity.ok("Update role successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Delete role successfully!");
    }
}