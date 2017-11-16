package com.itob.repository;

import com.itob.domain.UserTypeCredentialPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Based on the User type and Credential Type , Credential Policy is applied
 */
public interface UserTypeCredentialPolicyRepository extends JpaRepository<UserTypeCredentialPolicy, Long> {

    @Query("select utcp from UserTypeCredentialPolicy utcp inner join fetch  utcp.credentialPolicy cp " +
        "where utcp.userType.id = :idUserType and utcp.credentialType.id = :idCredentialType")
    Optional<UserTypeCredentialPolicy>
    fetchPolicyByUserTypeAndCredentialType(@Param("idUserType") Long idUserType,
                                           @Param("idCredentialType") Long idCredentialType);
}
