package com.hotel_booking_app.mapper;

import com.hotel_booking_app.Pojo.CV;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public interface CVMapper {
    ArrayList<CV> showAllCV();

    ArrayList<CV> showCVByState(@Param("state") String state);
}
