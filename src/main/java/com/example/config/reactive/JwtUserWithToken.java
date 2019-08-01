package com.example.config.reactive;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


public class JwtUserWithToken extends JwtUser {
    public static final String CLAIM_KEY_COMPANY = "company";
    private String token;
    private String company;

    public JwtUserWithToken(JwtUser jwtUser, String token) {
        super(jwtUser.getUsername(), jwtUser.getFirstname(), jwtUser.getLastname(), jwtUser.getEmail(), jwtUser.getAuthorities());
        this.token = token;
    }

    public JwtUserWithToken(JwtUser jwtUser, String token, String company) {
        this(jwtUser, token);
        this.company = company;
    }

    public String getToken() {
        return this.token;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String toAuditString() {
        return this.getLastname() + " " + this.getFirstname() + " (" + this.getUsername() + ")";
    }
}

