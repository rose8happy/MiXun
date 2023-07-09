package com.hotel_booking_app.mapper;

import com.hotel_booking_app.Pojo.CV;
import com.hotel_booking_app.Pojo.CV_q;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface CVMapper {
    ArrayList<CV> showAllCV();

    ArrayList<CV> showCVByState(@Param("state") String state);

    ArrayList<CV> conditionalQueryCV(@Param("CV") CV_q cv);
}
