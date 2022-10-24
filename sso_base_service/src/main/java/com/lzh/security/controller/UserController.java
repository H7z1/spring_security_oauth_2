package com.lzh.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author linzhihao
 * @Date 2022/10/22 3:09 下午
 * @Description
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/greeting")
    public String greeting(){
        return "Hello World";
    }
}
