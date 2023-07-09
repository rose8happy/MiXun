package com.hotel_booking_app.Pojo;

import lombok.Data;

@Data
public class CV {
    private long CV_ID;
    private String name;
    private String phone_num;
    private String gender;
    private String job_intention;
    private String specialized_subject;
    private String qualifications;
    private int service_years;
    private String creat_time;
    private String CV_source;
    private String CV_state;
}
