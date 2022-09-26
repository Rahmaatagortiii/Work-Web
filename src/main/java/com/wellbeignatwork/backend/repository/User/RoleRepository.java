package com.wellbeignatwork.backend.repository.User;

import com.wellbeignatwork.backend.entity.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
