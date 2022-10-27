package com.lzh.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

/**
 * @Author linzhihao
 * @Date 2022/10/22 3:09 下午
 * @Description
 */
@Api(tags = "api测试")
@RestController
@RequestMapping("/api")
public class UserController {

    @ApiOperation(value = "Hello",notes = "测试接口")
    @GetMapping("/greeting")
    public String greeting(){
        return "Hello World";
    }

    @ApiOperation(value = "POST Hello",notes = "POST 测试接口")
    @PostMapping("/greeting")
    public String makeGreeting(@RequestBody Profile profile){
        return "Hello World" + "!\n" + profile.gender;
    }

    @ApiOperation(value = "PUT Hello",notes = "PUT 测试接口")
    @PutMapping("/greeting/{name}")
    public String putGreeting(@PathVariable String name){
        return "Hello World"+ name;
    }

    @Data
    static class Profile {
        private String gender;
        private String idNo;
    }
}
