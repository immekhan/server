package com.itob.repository;

import com.itob.domain.OrgUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgUnitRepository extends JpaRepository<OrgUnit, String> {
}
