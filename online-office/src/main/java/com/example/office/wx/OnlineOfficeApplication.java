package com.example.office.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class OnlineOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineOfficeApplication.class, args);
    }

}
