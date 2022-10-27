package com.lzh.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzh.security.filter.RestAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author linzhihao
 * @Date 2022/10/24 6:24 下午
 * @Description
 */
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(req ->
                        req
                                .antMatchers("/authorize/**").permitAll()
                                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                                .antMatchers("/api/**").hasAnyRole("USER")
                                .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
    }

    private RestAuthenticationFilter restAuthenticationFilter() throws Exception {
        RestAuthenticationFilter filter = new RestAuthenticationFilter(objectMapper);
        filter.setAuthenticationFailureHandler(jsonAuthenticationFailureHandler());
        filter.setAuthenticationSuccessHandler(jsonAuthenticationSuccessHandler());
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/authorize/login");
        return filter;
    }

    private AuthenticationSuccessHandler jsonAuthenticationSuccessHandler(){
        return (req, res,auth) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            res.setStatus(HttpStatus.OK.value());
            res.getWriter().println(objectMapper.writeValueAsString(auth));
            log.debug("认证成功");
        };
    }

    private AuthenticationFailureHandler jsonAuthenticationFailureHandler(){
        return (req,res,exp) -> {
            val objectMapper = new ObjectMapper();
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF-8");
            final Map<String,String> map = new HashMap<>();
            map.put("title","认证失败");
            map.put("details",exp.getMessage());
            res.getWriter().println(objectMapper.writeValueAsString(map));
        };
    }

    /**
     * 设置默认用户信息
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                //使用默认的bcrypt
                .password(passwordEncoder().encode("123"))
                .roles("USER","ADMIN")
                .and()
                .withUser("zhangsan")
                //使用SHA-1编码
                .password("{SHA-1}" + new MessageDigestPasswordEncoder("SHA-1").encode("123"))
                .roles("USER");
    }

    /**
     * 密码加密组件
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        var idForDefault = "bcrypt";
        //key为加密后的{}值
        Map<String,PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForDefault,new BCryptPasswordEncoder());
        encoders.put("SHA-1",new MessageDigestPasswordEncoder("SHA-1"));
        //默认使用idForDefault
        return new DelegatingPasswordEncoder(idForDefault,encoders);
    }
}
