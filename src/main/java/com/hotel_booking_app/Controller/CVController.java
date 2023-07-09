package com.hotel_booking_app.Controller;

import com.hotel_booking_app.Pojo.CV;
import com.hotel_booking_app.mapper.CVMapper;
import com.hotel_booking_app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/CV")
public class CVController {
    @Autowired
    private CVMapper cvMapper;
    @GetMapping("/showAllCV")
    public ArrayList<CV> showAllCV(){
        ArrayList<CV> result = cvMapper.showAllCV();
        System.out.println(result);
        return result;
    }

    @GetMapping("/showCVByState")
    public ArrayList<CV> showCVByState(String state){
        return cvMapper.showCVByState(state);
    }

    @GetMapping("/queryCV")
    public ArrayList<CV> queryCV(String state){
        /**
         * 查询条件含 求职意向 姓名 日期范围 建立来源 联系电话 专业
         * 这么说来好像上面两个也可以被包含进来？
         */
        return cvMapper.showCVByState(state);
    }
}
