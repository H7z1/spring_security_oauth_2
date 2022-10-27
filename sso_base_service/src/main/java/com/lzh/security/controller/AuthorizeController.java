package com.lzh.security.controller;

import com.lzh.security.domain.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "认证模块")
@RestController
@RequestMapping("/authorize")
public class AuthorizeController {

    /**
     * localhost:8080/authorize/register
     * @param userDTO
     * @return
     */
    @ApiOperation(value = "注册",notes = "注册 接口")
    @PostMapping("/register")
    public UserDTO register(@Valid @RequestBody UserDTO userDTO){
        return userDTO;
    }
}
