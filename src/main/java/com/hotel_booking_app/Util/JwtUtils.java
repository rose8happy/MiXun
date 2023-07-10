package com.hotel_booking_app.Util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class JwtUtils {

    //@Value("jwt.secret")
    private String SECRET_KEY = "3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAABlwAAAAdzc2gtcn" +
            "NhAAAAAwEAAQAAAYEAzZKCGOuSdd5RCbdhBFZi96R39tZtk/SlR5W71V9S3CczGwQJpY5f" +
            "3CMTLwMJv/VNV8/c/C3REtn1LFJFTayTRJy3j2qauYWT/w8jOHqsMsfNZRex6s/AQ+w2X5" +
            "kxQbOKHGzZ7VhZU8WOZfWEefcJr++rn9nG+uMaaLr5yHeIgCEUW9/nyU18W6uGVRYQ+H8d" +
            "dgtDPIN8bzb9/BuCwsOAUFjDHmO9COX7Pj5rjZp4HkdboHs8csKWIryGWSqqEoH0EWUdyr" +
            "tOb7EQEpLoGGhp406rjWrjZbgL5n+Gw5Gmj6Ew/jqz7SOvLpA2b01PSBBJro2GHjvQen63" +
            "LLEFC9h2CW/eUr6sxP2c2eznQPBJ2sXVoooJtCA26QEdqKlDCQx5av5SdVNubwBD+djdRl" +
            "zZrv4TLV53RZKpluzfWQAzY35JtLch5HA+7lWNRtNZ76LfyowOmCE4H7pcainj9HQzi8EG" +
            "KIjvv8NohEY4C3gF3LNhE9OgMsyHXKkr0rGgizS7AAAFiIml9h+JpfYfAAAAB3NzaC1yc2" +
            "EAAAGBAM2SghjrknXeUQm3YQRWYvekd/bWbZP0pUeVu9VfUtwnMxsECaWOX9wjEy8DCb/1" +
            "TVfP3Pwt0RLZ9SxSRU2sk0Sct49qmrmFk/8PIzh6rDLHzWUXserPwEPsNl+ZMUGzihxs2en" +
            "1YWVPFjmX1hHn3Ca/vq5/ZxvrjGmi6+ch3iIAhFFvf58lNfFurhlUWEPh/HXYLQzyDfG82" +
            "/fwbgsLDgFBYwx5jvQjl+z4+a42aeB5HW6B7PHLCSa6Nhh470Hp+tyyxBQvYdglv" +
            "3lK+rMT9nNns50DwSdrF1aKKCbQgNukBHaipQwkMeWr+UnVTbm8AQ/nY3UZc2a7+Ey1ed0" +
            "WSqZbs31kAM2N+SbS3IeRwPu5VjUbTWe+i38qMDpghOB+6XGop4/R0M4vBBiiI77/DaIRG" +
            "OAt4BdyzYRPToDLMh1ypK9KxoIs0uwAAAGAb7CVHKMJDR8/2k6W2HtzA+eJAk" +
            "SYfpWiIxWYWyKSa2ik3J3B/AAx1vH7n0RATNojLn2SLDVcbaicC3ubicskrFeVZ8dcz0Vjn" +
            "FmYMFciYvHJQBwfrs48yf/Tbye76kx7QAAeoE8s3pUslxgGx+24cBknqbkwtTuyK0bIcuin" +
            "0vraXLYOtzDK0bztaPgrUdfVXbFOKjokIyHkcf2Mm2QCQ6nb4ItiJO9VALExrzM+q" +
            "WbP7MMLGPZM+Tz3d73ZIwsR7/k5jmbD683j9ICC+iBcXigCTDmLiQfDQx6eDUNZ4iPWCWu" +
            "w2KeLfJpTnr0baxy9/E0FPKKyBFbFiYPU4vJjNYyvyvs/7G//Nsicqnojs8lF8+duf6IP7n" +
            "qCM/wDl8hyq0PgM8Yv/KEsBw7cz2ag56+JeYWUY/S82SDIOFnZDi3YEsOH2w0cEjlOW" +
            "BMfjgfEGc3d51IJ33PxCL+bPGkP4KKcIkH9CSXP0kDo1/ShLQfQjJHWRGDGi1kdZTRAAAA" +
            "wQDRi/GGHJg9WSbQNnHGyCeVeZSMVlRCVGtNSdSV+tk7ON0CakZfJ+n6Uv5Bn3TIaIPEnr" +
            "6qC3GKpxQU4djK2BixtLWRTuUyu3szyopczLenaDBjtPjfnKlX6eD3x5vsFWEbLPvZqUt6" +
            "bXBcwhtfGsDm1PppntCCoGA5VA9QKGNwaiQF+IAgwZP6xM/+tS6U4I5jD/gcu/b7zhNLeu" +
            "qj65ufrExSdKc+/X+QjckJyDEJQCFew4WQwLGZBSzt1LtmgtMAAADBAOmaiXDSsuq5E7Sd" +
            "lXAEQ/sT5ksVlu+vTbAr/kEGZIc3VcFgeX7JSN8HF3gCKZyDSzMdZaQGRmmZkE1fl8eEXo" +
            "CeIX0vx8rKRTt17dZesTwJrIQ0QTRP7QsGoQwuFmlTrfUkQiozQzHJibkK86ZO6uaCylnP" +
            "Cu7EapEvYGjBB/ceuFoh0/dgApAf1L/lY6IzA0I8tdeTYoX/Ry92da33CBAvUQ9ZvYunos" +
            "7JHhDGp429t+OPLJ6DWPXBUAt62EL4cwAAAMEA4Uf7b+nJihkPfssqfRisk6HdZq7YPdOJ" +
            "DbgLuyjyldWchQ6o45F4qj8caRGKzDWPnHGG5vTLRMGQqJ3CHbdNqo5itnmiqYcuQ7uO3A" +
            "zikTUblf7hYx2Zl29Wip2dAEicLUdBnbsPCFrGkn0lnuP3LFpgo0kEEbzzeyb9NvHBZxei" +
            "R+WM5k8tgPDAgt1xpwerthxG5YkKM2kFxB+Ebx";

    // 定义一个令牌有效期，单位为毫秒
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // 生成令牌的方法，接收一个用户名作为参数
    public  String generateToken(String username) {
        //System.out.println("sss: "+SECRET_KEY);
        // 使用JwtBuilder创建一个令牌
        JwtBuilder builder = Jwts.builder()
                .setSubject(username) // 设置主题
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY); // 设置签名算法和密钥
        // 返回生成的令牌字符串
        return builder.compact();
    }

    // 解析令牌的方法，接收一个令牌字符串作为参数，返回一个用户名
    public  String parseToken(String token) {
        // 使用JwtParser解析令牌
        JwtParser parser = Jwts.parser()
                .setSigningKey(SECRET_KEY); // 设置密钥
        // 获取令牌中的主体
        String username = parser.parseClaimsJws(token)
                .getBody()
                .getSubject();
        // 返回主体
        return username;
    }

    // 验证令牌的方法，接收一个令牌字符串作为参数，返回一个布尔值，表示令牌是否有效
    public  boolean validateToken(String token) {
        try {
            // 使用JwtParser解析和验证令牌，如果成功则返回true，如果失败则抛出异常
            // 过期会自动引发异常呢
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 捕获异常并返回false
            return false;
        }
    }
}