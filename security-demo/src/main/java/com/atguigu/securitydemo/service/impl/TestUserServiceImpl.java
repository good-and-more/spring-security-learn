package com.atguigu.securitydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.securitydemo.entity.TestUser;
import com.atguigu.securitydemo.service.TestUserService;
import com.atguigu.securitydemo.mapper.TestUserMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser>
implements TestUserService{

}




