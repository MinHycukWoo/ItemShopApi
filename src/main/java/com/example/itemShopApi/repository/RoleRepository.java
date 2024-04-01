package com.example.itemShopApi.repository;

import com.example.itemShopApi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * @Auther : Leo Woo
 * @mailto : edit020162@gamil.com
 * @Create : 2024/03/31
 */
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
