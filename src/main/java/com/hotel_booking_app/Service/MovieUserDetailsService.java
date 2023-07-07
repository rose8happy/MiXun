//package com.hotel_booking_app.Service;
//
//import com.hotel_booking_app.Pojo.User;
//import com.hotel_booking_app.mapper.UserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
///*
//验证用户名和密码对不对
// */
//@Service
//public class MovieUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        // 根据用户名从数据库中查询用户信息
//        User user = userMapper.getByUserName(userName);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("用户不存在");
//        }
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        // 返回一个UserDetails对象，包含用户名、密码和权限
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                "{bcrypt}"+encoder.encode(user.getPassword()),
//                //user.getAuthorities()
//                //正常情况下不是all，这里是因为作者没用到权限
//                AuthorityUtils.commaSeparatedStringToAuthorityList("all")
//        );
//    }
//
//}
//
