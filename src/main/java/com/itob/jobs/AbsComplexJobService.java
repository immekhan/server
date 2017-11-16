package com.itob.jobs;

import com.itob.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("absComplexJobService")
public class AbsComplexJobService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UtilityService absUtilityService;

    protected void execute() {

//        absUtilityService.fetchCountriesDTO();

    }

}
