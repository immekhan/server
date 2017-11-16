package com.itob.repository;

import com.itob.domain.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorCodeRepository extends JpaRepository<ErrorCode, Long> {
}
