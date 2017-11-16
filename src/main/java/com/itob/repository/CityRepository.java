package com.itob.repository;

import com.itob.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select city from City city where city.idCountry = :idCountry")
    Optional<List<City>> fetchByCountryId(@Param("idCountry") Long idCountry);
}
