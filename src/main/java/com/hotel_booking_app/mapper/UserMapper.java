package com.hotel_booking_app.mapper;

import com.hotel_booking_app.Pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int insertUser(User user);

    User getByUserName(@Param("userName") String userName);
}