package com.itob.repository;

import com.itob.domain.Privileges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privileges, String> {
}
