/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.railway.com.portal.entity;

/**
 * 用户.
 * 
 * @author calvin
 */
public class User {
    private int id;

    private String username;

    private String name;

    private String password;

    private String ljpym;

    private String ljjc;

    private String ljqc;

    private String deptname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLjpym() {
        return ljpym;
    }

    public void setLjpym(String ljpym) {
        this.ljpym = ljpym;
    }

    public String getLjjc() {
        return ljjc;
    }

    public void setLjjc(String ljjc) {
        this.ljjc = ljjc;
    }

    public String getLjqc() {
        return ljqc;
    }

    public void setLjqc(String ljqc) {
        this.ljqc = ljqc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}