package com.itob.jobs;

import com.itob.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("jobone")
public class AbsSimpleJobOne {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UtilityService absUtilityService;
    protected void myTask() {
    	System.out.println("This is my task");
    }
}
