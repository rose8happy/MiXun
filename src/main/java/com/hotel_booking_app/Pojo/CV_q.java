package com.hotel_booking_app.Pojo;

import lombok.Data;

import java.sql.Date;

@Data
public class CV_q {
    private long CV_ID;
    private String name;
    private String phone_num;
    private String gender;
    private String job_intention;
    private String specialized_subject;
    private String qualifications;
    private int service_years;
    private Date create_time_b; //为了便于mybatis查询修改了该属性名
    private Date create_time_a; //为了便于mybatis查询增加了该属性名
    private String CV_source;
    private String CV_state;
    //据某博客说是序列化对象用的，postman时需要，前端一般会序列化好
    private static final long serialVersionUID = 1L;
}
