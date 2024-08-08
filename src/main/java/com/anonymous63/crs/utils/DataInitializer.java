package com.anonymous63.crs.utils;

import com.anonymous63.crs.exceptions.ResourceNotFoundException;
import com.anonymous63.crs.models.Permission;
import com.anonymous63.crs.models.Role;
import com.anonymous63.crs.repositories.PermissionRepo;
import com.anonymous63.crs.repositories.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;

    public DataInitializer(RoleRepo roleRepo, PermissionRepo permissionRepo) {
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public void run(String... args) {

        List<Permission> permissions = Arrays.asList(
                new Permission(1L, "CREATE"),
                new Permission(2L, "READ"),
                new Permission(3L, "UPDATE"),
                new Permission(4L, "DELETE"),
                new Permission(5L, "DISABLE"),
                new Permission(6L, "ENABLE"),
                new Permission(7L, "FIND_ALL"),
                new Permission(8L, "FIND_BY_ID"),
                new Permission(9L, "IMPORT"),
                new Permission(10L, "EXPORT")
        );
        this.permissionRepo.saveAll(permissions);

        Permission createPermission = this.permissionRepo.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Permission", "id", 1L));
        Permission readPermission = this.permissionRepo.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Permission", "id", 1L));
        Permission updatePermission = this.permissionRepo.findById(3L).orElseThrow(() -> new ResourceNotFoundException("Permission", "id", 1L));
        Permission deletePermission = this.permissionRepo.findById(4L).orElseThrow(() -> new ResourceNotFoundException("Permission", "id", 1L));
        Permission disablePermission = this.permissionRepo.findById(5L).orElseThrow(() -> new ResourceNotFoundException("Permission", "id", 1L));
        Permission enablePermission = this.permissionRepo.findById(6L).orElseThrow(() -> new ResourceNotFoundException("Permission", "id", 1L));
        Permission fineAllPermission = this.permissionRepo.findById(7L).orElseThrow(() -> new ResourceNotFoundException("Permission", "id", 1L));
        Permission findByIdPermission = this.permissionRepo.findById(8L).orElseThrow(() -> new ResourceNotFoundException("Permission", "id", 1L));
        Permission importPermission = this.permissionRepo.findById(9L).orElseThrow(() -> new ResourceNotFoundException("Permission", "id", 1L));
        Permission exportPermission = this.permissionRepo.findById(10L).orElseThrow(() -> new ResourceNotFoundException("Permission", "id", 1L));


        // Create roles
        Role adminRole = createRole(1L, "ADMIN", Arrays.asList(createPermission, readPermission, updatePermission, deletePermission, disablePermission, enablePermission, fineAllPermission, findByIdPermission, importPermission, exportPermission));
        Role userRole = createRole(2L, "USER", Arrays.asList(readPermission, fineAllPermission, findByIdPermission));

        // Save roles
        roleRepo.save(adminRole);
        roleRepo.save(userRole);
    }

    private Role createRole(Long id, String name, List<Permission> permissions) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.getPermissions().addAll(permissions);
        return role;
    }

}

