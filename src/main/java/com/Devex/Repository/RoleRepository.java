package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Role;

@EnableJpaRepositories
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, String>{

}
