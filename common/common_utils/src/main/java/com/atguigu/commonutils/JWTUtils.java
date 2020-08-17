package com.atguigu.commonutils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;



/**
 * @author helen
 * @since 2019/10/16
 */
public class JWTUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;      //过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";       //密钥，每个公司不一样

    /**
     * 生成token字符串
     * @param id
     * @param nickname
     * @return
     */
    public static String getJwtToken(String id, String nickname){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")   //设置规则为JWT
                .setHeaderParam("alg", "HS256")
                .setSubject("guli-user")    //设置分1，随便起的名，需要更改
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))   //设置过期时间，超过这个时间就过期了
                .claim("id", id)    //设置用户的id，可以存储用户其他信息，可以是一个用户对象
                .claim("nickname", nickname)        //设置用户昵称
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");//要求token是放在请求头中的，注意，是放在请求头而不是cookie中
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");   //要求token是放在请求头中的，注意，是放在请求头而不是cookie中
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();//得到字符串中的主体部分
        return (String)claims.get("id");
    }



    /**
     * 根据token获取会员昵称
     * @param request
     * @return
     */
    public static String getMemberNicknameByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");   //要求token是放在请求头中的，注意，是放在请求头而不是cookie中
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();//得到字符串中的主体部分
        return (String)claims.get("nickname");
    }
}
