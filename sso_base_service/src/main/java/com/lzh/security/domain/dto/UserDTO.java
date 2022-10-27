package com.lzh.security.domain.dto;

import com.lzh.security.validator.annotation.PasswordMatch;
import com.lzh.validator.annotation.ValidEmail;
import com.lzh.validator.annotation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author linzhihao
 * @Date 2022/10/26 6:30 下午
 * @Description
 */
@Data
@PasswordMatch
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -6272910676874538689L;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 4,max = 50,message = "用户名长度必须在4到50个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ValidPassword
    private String password;

    @NotBlank(message = "邮箱不能为空")
//    @Email(message = "邮箱格式有误")
    //由于@Email验证格式并不够严格，可用正则表达式来验证
//    @Pattern(
//            regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[_A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
//            ,message = "邮箱格式有误")
    @ValidEmail(message = "邮箱格式有误")
    private String email;

    @NotBlank(message = "姓名不能为空")
    @Size(min = 4,max = 50,message = "姓名长度必须在4到50个字符之间")
    private String name;

    @NotBlank(message = "密码不能为空")
    private String matchingPassword;
}
