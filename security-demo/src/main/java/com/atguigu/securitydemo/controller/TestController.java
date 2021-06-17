package com.atguigu.securitydemo.controller;

import com.atguigu.securitydemo.entity.TestUser;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello security";
    }

    @GetMapping("/index")
    public String index() {
        return "hello index";
    }

    @GetMapping("/update")
    @Secured({"ROLE_sale","ROLE_manager"})
    public String update() {
        return "hello update";
    }

    @GetMapping("/insert")
    @PreAuthorize("hasAnyAuthority('admins')")
    public String insert() {
        return "hello insert";
    }

    @GetMapping("/result")
    @PostAuthorize("hasAnyAuthority('admins')")
    public String result() {
        System.out.println("result");
        return "hello result";
    }

    @RequestMapping("getAll")
    @PreAuthorize("hasAnyAuthority('admins')")
    @PostFilter("filterObject.name == 'admin1'")
    public List<TestUser> getAllUser(){
        ArrayList<TestUser> list = new ArrayList<>();
        list.add(new TestUser(11,"admin1","6666"));
        list.add(new TestUser(21,"admin2","888"));
        System.out.println(list);
        return list;
    }

    @RequestMapping("getTestPreFilter")
    @PreAuthorize("hasAnyAuthority('admins')")
    @PreFilter(value = "filterObject.id%2==0")
    public List<TestUser> getTestPreFilter(List<TestUser> list){
        list.forEach(t-> {
            System.out.println(t.getId()+"\t"+t.getName());
        });
        return list;
    }
}
