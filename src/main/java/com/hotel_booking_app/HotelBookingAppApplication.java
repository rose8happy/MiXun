package com.hotel_booking_app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@MapperScan("com.hotel_booking_app.mapper")
public class HotelBookingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingAppApplication.class, args);
    }

}
