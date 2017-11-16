package com.itob.repository;

import com.itob.domain.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    /**
     * Fetch user credentials by type , user id and with status Id of credentials
     * @param credentialTypes
     * @param isActive
     * @param userId
     * @return
     */
    @Query("select cred from UserCredentials cred where cred.credentialType in (:credentialTypes) " +
        "and cred.dbActive = :isActive and cred.idUser = :userId and cred.credentialStatus = :credentialStatusId")
    Optional<UserCredentials> fetchByCredentialTypeAndUserId(
        @Param("credentialTypes") ArrayList<Long> credentialTypes,
        @Param("isActive") Character isActive,
        @Param("userId") Long userId,
        @Param("credentialStatusId") Long statusId
    );
}
