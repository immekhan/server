package com.itob.repository;

import com.itob.domain.AbsUser;
import com.itob.repository.api.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsUserRepository extends JpaRepository<AbsUser, Long>, UserRepositoryCustom {
}
