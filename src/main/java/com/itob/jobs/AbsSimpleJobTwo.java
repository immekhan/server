package com.itob.jobs;

import org.springframework.stereotype.Service;

@Service("jobtwo")
public class AbsSimpleJobTwo {
    protected void myTask() {
    	System.out.println("This is my task 2");
    }
}
