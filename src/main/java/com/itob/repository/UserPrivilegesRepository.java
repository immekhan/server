package com.itob.repository;

import com.itob.domain.UserPrivileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserPrivilegesRepository extends JpaRepository<UserPrivileges, Long> {


    /**
     * Fetch the user Privileges assigned to a user by its userId and orgUnitId
     * @param userId
     * @param orgUnitId
     * @return
     */
    @Query("select uPriv.idPrivilege from UserPrivileges uPriv where uPriv.idUser = :userId " +
        "and uPriv.idOrgUnit = :orgUnitId")
    Optional<List<String>> fetchByUserId(@Param("userId") Long userId,
                                         @Param("orgUnitId") String orgUnitId);
}
