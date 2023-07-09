package com.hotel_booking_app.Controller;

import com.hotel_booking_app.Pojo.CV;
import com.hotel_booking_app.Pojo.CV_q;
import com.hotel_booking_app.mapper.CVMapper;
import com.hotel_booking_app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/CV")
public class CVController {
    @Autowired
    private CVMapper cvMapper;
    @GetMapping("/showAllCV")
    public ArrayList<CV> showAllCV(){
        ArrayList<CV> result = cvMapper.showAllCV();
        //System.out.println(result);
        return result;
    }

    @GetMapping("/showCVByState")
    public ArrayList<CV> showCVByState(String state){
        return cvMapper.showCVByState(state);
    }

    @PostMapping("/queryCV")
    public ArrayList<CV> queryCV(@RequestBody CV_q cv_q){
        //有博客说如果使用@RequestBody必须用Post如果用get参数会传递不过去。
        //并且参数需要是json格式的字符串
        //并且请求中必须带上请求头（疑似是说编码格式"Content-Type" : "application/json;charset=utf-8"
        /**
         * 查询条件含 求职意向 姓名 日期范围 简历来源 联系电话 专业
         * 这么说来好像上面两个也可以被包含进来？
         */
        ArrayList<CV> result = cvMapper.conditionalQueryCV(cv_q);
        return result;
    }
}
