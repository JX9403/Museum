package com.web.museum.security;


public class EndPoints {

    public static final String front_end_host = "http://localhost:3000";
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/api/works",
            "/api/works/**",
            "/api/authors",
            "/api/authors/**",
            "/api/works",
            "/api/works/**",
            "/api/img",
            "/api/img/**",
            "/api/users/search/existsByEmail",
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/account/register",
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/api/users",
            "/api/users/**"
    };
}