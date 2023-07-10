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
        //可以通过自建空的CV_q来实现查询全部
        CV_q cv_q = new CV_q();
        ArrayList<CV> result = cvMapper.conditionalQueryCV(cv_q);
        return result;
    }

    @GetMapping("/showCVByState")
    public ArrayList<CV> showCVByState(String state){
        //可以通过其他为空，state有值来实现
        CV_q cv_q = new CV_q();
        cv_q.setCV_state(state);
        System.out.println(cv_q);
        ArrayList<CV> result = cvMapper.conditionalQueryCV(cv_q);
        return result;
    }

    @GetMapping("/queryCV")
    public ArrayList<CV> queryCV(@RequestBody CV_q cv_q){
        //有博客说如果使用@RequestBody必须用Post如果用get参数会传递不过去。
        //并且参数需要是json格式的字符串
        //并且请求中必须带上请求头（疑似是说编码格式"Content-Type" : "application/json;charset=utf-8"
        /**
         * 查询条件含 求职意向 姓名 日期范围 简历来源 联系电话 专业
         * 这么说来好像上面两个也可以被包含进来？
         * 不过state作为常用查询条件，不用传整个CV_q方便其查询也是有道理的？
         */
        //System.out.println("queryCV: I'm in");
        ArrayList<CV> result = cvMapper.conditionalQueryCV(cv_q);//这行代码有问题
        return result;
    }
}
