package com.example.office.wx.config.shiro;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${office.jwt.secret}")
    private String secret;

    @Value("${office.jwt.expire}")
    private int expire;

    /**
     * 根据过期时间、秘钥、userId生成过期字符串
     *
     * @param userId
     * @return
     */
    public String createToken(int userId) {
        //当前时间偏移5天
        Date date = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 5);
        //加密算法 生成秘钥
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create();
        String token = builder.withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
        return token;
    }

    /**
     * 从令牌字符串中反向得到userId
     *
     * @param token
     * @return
     */
    public int getUserId(String token) {
        //对token进行解码
        DecodedJWT jwt = JWT.decode(token);
        int usrId = jwt.getClaim("userId").asInt();
        return usrId;
    }

    /**
     * 验证令牌字符串的有效性
     *
     * @param token
     */
    public void verifierToken(String token) {
        //创建算法对象
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //构建验证对象
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        //验证token， 如果验证通过，方法结束，没有通过抛出RunTime异常
        jwtVerifier.verify(token);
    }
}
