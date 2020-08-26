package com.miracle.weaver.jwt;


public class JWTProperties {
    public static final String SECRET = "testSecret";
    public static final int EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
