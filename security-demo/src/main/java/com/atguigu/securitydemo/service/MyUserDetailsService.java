package com.atguigu.securitydemo.service;

import com.atguigu.securitydemo.entity.TestUser;
import com.atguigu.securitydemo.mapper.TestUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    //@Autowired
    //private TestUserMapper testUserMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        //QueryWrapper<TestUser> wrapper = new QueryWrapper<>();
        //wrapper.eq("name",name);
        //TestUser testUser = testUserMapper.selectOne(wrapper);
        //
        //if(null == testUser) { //数据库没有用户名，认证失败
        //    throw new UsernameNotFoundException("用户名不存在");
        //}
        //List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_sale,admins");
        //return new User(testUser.getName(),new BCryptPasswordEncoder().encode(testUser.getPassword()),auths);

        //这一段用来测试CSRF
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("role"));
        UserDetails userDetails = new User("mary", new BCryptPasswordEncoder().encode("123")
                , list);
        return userDetails;
        //这一段用来测试CSRF
    }
}
