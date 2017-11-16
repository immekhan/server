package com.itob.repository;

import com.itob.domain.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {


    /**
     * Fetch the user Roles assigned to a user by its userId and orgUnitId
     * @param userId
     * @param orgUnitId
     * @return
     */
    @Query("select uRole.idRole from UserRoles uRole where uRole.idUser = :userId " +
        "and uRole.idOrgUnit = :orgUnitId")
    Optional<List<String>> fetchByUserId(@Param("userId") Long userId,
                                         @Param("orgUnitId") String orgUnitId);
}
