package com.itob.repository;

import com.itob.domain.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    @Query("select address from UserAddress address where address.user.id = :userId " +
        "and address.addressType =:addressType and address.addressStatus = :addressStatus")
    Optional<UserAddress> fetchByUserIdAddressTypeAndStatus(@Param("userId") Long userId,
                                                            @Param("addressType") Long addressType,
                                                            @Param("addressStatus") Long addressStatus);
}
