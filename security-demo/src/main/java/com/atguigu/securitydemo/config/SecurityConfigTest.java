package com.atguigu.securitydemo.config;

import com.atguigu.securitydemo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    //这一块不懂为什么要先注入一个Bean返回BCryptPasswordEncoder，底下方法不是new了一个该对象吗？
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//自定义自己编写的登录页面
            .loginPage("/login.html")//登录页面设置
            .loginProcessingUrl("/user/login")//登录访问路径
            .defaultSuccessUrl("/test/index").permitAll()//登录成功之后跳转的路径
            .and().authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll()//设置哪些路径可以直接访问，不需要认证
                //在配置类设置当前登录用户，只有具有admins权限才可以访问这个路径
                //1.hasAuthority方法
                //.antMatchers("/test/index").hasAuthority("admins,manager")
                //2.hasAnyAuthority方法
                //.antMatchers("/test/index").hasAnyAuthority("admins,manager")
                //3.hasRole方法 ROLE_sale
                //.antMatchers("/test/index").hasRole("sale")
                //4.hasAnyRole方法
                .antMatchers("/test/index").hasAnyRole("sale,admin")
            .anyRequest().authenticated()
            .and().csrf().disable();//关闭scrf防护
    }
}
