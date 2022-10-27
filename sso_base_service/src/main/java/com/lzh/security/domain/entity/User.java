package com.lzh.security.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -3287604067331781033L;

    private String username;

    private String password;

    private String email;

    private String name;


}
