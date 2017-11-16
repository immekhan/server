package com.itob.repository;

import com.itob.domain.DummyForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the AbsDummyFormBlob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DummyFormRepository extends JpaRepository<DummyForm, Long> {

}
