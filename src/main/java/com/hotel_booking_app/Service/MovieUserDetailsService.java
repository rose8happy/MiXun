package com.hotel_booking_app.Service;

import com.hotel_booking_app.Pojo.User;
import com.hotel_booking_app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/*
验证用户名和密码对不对
 */
@Service
public class MovieUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 根据用户名从数据库中查询用户信息
        User user = userMapper.getByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //System.out.println(user.getPassword());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 返回一个UserDetails对象，包含用户名、密码和权限
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                //"{bcrypt}"+encoder.encode(user.getPassword()),
                user.getPassword(),
                //user.getAuthorities()
                //正常情况下不是all，这里是因为作者没用到权限
                AuthorityUtils.commaSeparatedStringToAuthorityList("user")
        );
    }

}


/*
不使用这个userservice好像也能自动查询？？？
使用了之后验权会失败
要在SecurityConfig里面加配置
不配置也能用虽然不知道为什么，security好像是会自动查询数据库？那mybatis也白写了？
* */