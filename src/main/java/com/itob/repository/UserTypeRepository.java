package com.itob.repository;

import com.itob.domain.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {

    @Query("select userType from UserType userType where userType.id in (:userTypes)")
    Optional<List<UserType>> findAllByUserTypes(@Param("userTypes") List<Long> userTypes);


    @Query("select userType from UserType userType where userType.idRole like :roleCategory " +
        "and userType.idOrgUnit = :idOrgUnit")
    Optional<List<UserType>> findAllByUserTypesCategory(@Param("roleCategory") String roleCategory,
                                                        @Param(("idOrgUnit")) String idOrgUnit);


}
