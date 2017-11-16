package com.itob.repository;

import com.itob.domain.UserIdentification;
import com.itob.repository.api.UserIdentificationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserIdentificationRepository extends JpaRepository<UserIdentification, Long>, UserIdentificationRepositoryCustom {

    /**
     * fetch the user identification by identification type , active statuses and identification value
     * @param identityTypes
     * @param isActive
     * @param orgUnitId
     * @param identification
     * @return
     */
    @Query("select iden from UserIdentification iden inner join fetch iden.user user inner join fetch " +
        "user.orgUnit inner join fetch user.userType where iden.identificationType in (:identityTypes) " +
        "and iden.identification = :identification and iden.dbActive in (:isActive) and iden.orgUnit.id = :orgUnitId")
    Optional<List<UserIdentification>> fetchByIdentityType(@Param("identityTypes") ArrayList<Long> identityTypes,
                                                           @Param("isActive") ArrayList<Character> isActive,
                                                           @Param("orgUnitId") String orgUnitId,
                                                           @Param("identification") String identification);

    /**
     * fetch the user identification by identification type and active statuses and user id
     * @param identityTypes
     * @param isActive
     * @param orgUnitId
     * @param userId
     * @return
     */
    @Query("select iden from UserIdentification iden inner join fetch iden.user user inner join fetch " +
        "user.orgUnit inner join fetch user.userType where iden.identificationType in (:identityTypes) " +
        "and iden.dbActive in (:isActive) and iden.orgUnit.id = :orgUnitId and iden.user.id =:userId")
    Optional<List<UserIdentification>> fetchByUserIdIdentityType(@Param("identityTypes") ArrayList<Long> identityTypes,
                                                                 @Param("isActive") ArrayList<Character> isActive,
                                                                 @Param("orgUnitId") String orgUnitId,
                                                                 @Param("userId") Long userId);


}
