package org.csu.jpetstore.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;


public class JwtUtil {
    //私钥
    public static final String SECRET = "CaiGuanyuZhangLujunChenJiaqi";
    //过期时间2h(ms)，超过这个时间发一个新的token
    public static final long EXPIRE_TIME = 2*60*60*1000;
    //完全失效时间2d(ms)，超过这个时间token失效且不再发新的token
    public static final long FAILURE_TIME = 2*24*60*60*1000;

    /**
     * 生成一个jwt字符串
     *
     * @param name       用户名
     * @param secret     秘钥
     * @param expireTime 超时时间（单位ms）
     * @return java.lang.String
     */
    public static String encode(String name, String secret, long expireTime) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                //签发时间
                .withIssuedAt(new Date())
                //过期时间
                .withClaim("expi",new Date(System.currentTimeMillis() + expireTime))
                .withClaim("name", name)
                .sign(algorithm);
        return token;
    }


    /**
     * 由已设定好的过期时间和签发时间生成一个jwt字符串
     *
     * @param name    name
     * @param expDate 设定的过期时间戳
     * @param secret  secret
     * @return
     */
    public static String encode(String name, Date expDate,Date iatDate ,String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                //签发时间
                .withIssuedAt(iatDate)
                //过期时间
                .withClaim("expi",expDate)
                //设置负载
                .withClaim("name", name)
                .sign(algorithm);
        return token;
    }

    /**
     * token的基础判断，通过再加密判断token是否由server签发
     *
     * @param token
     * @param secret
     * @return
     */
    public static boolean isJwtValid(String token, String secret) throws TokenExpiredException {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false;
        } else {
            try {
                Map<String, Claim> claimsMap = decode(token, secret);
                String newToken = encode(claimsMap.get("name").asString(), claimsMap.get("expi").asDate(),
                        claimsMap.get("iat").asDate(), secret);
                String[] newParts = newToken.split("\\.");
                if (parts[2].equals(newParts[2])) {
                    return true;
                } else
                    return false;
            } catch (SignatureVerificationException | JWTDecodeException ex ) {
                //签名无效导致解析失败
                return false;
            }
        }
    }

    /**
     * 判断token是否过期
     *
     * @param token  token
     * @param secret secret
     * @return
     */
    public static boolean isJwtExpired(String token, String secret) {
        Map<String, Claim> claimsMap = decode(token, secret);
        Date expiredDate = claimsMap.get("expi").asDate();
        Date now = new Date();
        return expiredDate.getTime() < now.getTime();
    }

    /**
     * 判断token是否完全faliure
     *
     * @param token       token
     * @param secret      secret
     * @param failureTime 完全失效时间
     * @return
     */
    public static boolean isJwtFailure(String token, String secret, long failureTime) {
        Map<String, Claim> claimsMap = decode(token, secret);
        Date IssuedAt = claimsMap.get("iat").asDate();
        Date failureDate = new Date(IssuedAt.getTime()+failureTime);
        Date now = new Date();
        return failureDate.getTime() < now.getTime();
    }

    /**
     * 通过secret解密token，获取playload中的键值对
     *
     * @param token  token
     * @param secret secret
     * @return java.util.Map<java.lang.String, com.auth0.jwt.interfaces.Claim>
     */
    public static Map<String, Claim> decode(String token, String secret) throws SignatureVerificationException,
            JWTDecodeException {
        if (token == null || token.length() == 0) {
            return null;
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaims();
    }
}
