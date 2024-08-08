package com.anonymous63.crs.repositories;

import com.anonymous63.crs.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<Permission, Long> {
}
