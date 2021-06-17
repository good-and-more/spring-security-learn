//package com.atguigu.securitydemo.config;
//
//import com.atguigu.securitydemo.service.MyUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
//public class SecurityConfigTest extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    //注入数据源
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(dataSource);
//        //jdbcTokenRepository.setCreateTableOnStartup(true);设置创建数据源时自动创建表,此处因为已经创建了就注释掉了
//        return jdbcTokenRepository;
//    };
//
//    //这一块不懂为什么要先注入一个Bean返回BCryptPasswordEncoder，底下方法不是new了一个该对象吗？
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //配置退出
//        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();
//        //配置没有权限时跳转自定义界面
//        http.exceptionHandling().accessDeniedPage("/unauth.html");
//        http.formLogin()//自定义自己编写的登录页面
//            .loginPage("/login.html")//登录页面设置
//            .loginProcessingUrl("/user/login")//登录访问路径
//            .defaultSuccessUrl("/success.html").permitAll()//登录成功之后跳转的路径
//            .and().authorizeRequests()
//                .antMatchers("/","/test/hello","/user/login").permitAll()//设置哪些路径可以直接访问，不需要认证
//                //在配置类设置当前登录用户，只有具有admins权限才可以访问这个路径
//                //1.hasAuthority方法
//                //.antMatchers("/test/index").hasAuthority("admins,manager")
//                //2.hasAnyAuthority方法
//                //.antMatchers("/test/index").hasAnyAuthority("admins,manager")
//                //3.hasRole方法 ROLE_sale
//                //.antMatchers("/test/index").hasRole("sale")
//                //4.hasAnyRole方法
//                .antMatchers("/test/index").hasAnyRole("sale,admin")
//            .anyRequest().authenticated()
//
//            .and().rememberMe().tokenRepository(persistentTokenRepository())
//            .tokenValiditySeconds(120)
//            .userDetailsService(userDetailsService)
//
//            .and().csrf().disable();//关闭scrf防护
//    }
//}
