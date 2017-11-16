package com.itob.repository;


import com.itob.domain.RolePrivilege;
import com.itob.repository.api.RolePrivilegeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege,RolePrivilege.RolePrivilegePk>, RolePrivilegeRepositoryCustom {

    @Query("select rp.id.privilege from RolePrivilege rp where rp.id.role in (:idRoles) ")
    Optional<List<String>> findPrivilegesByRoles(@Param("idRoles") List<String> idRoles);

}
