package com.atguigu.security.filter;

import com.atguigu.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter{

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取当前认证成功用户权限信息
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
        //判断如果有权限信息，放到权限上下文中
        if(authRequest != null) {
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(token != null) {
            //从token获取用户名
            String username = tokenManager.getUserInfoFromToken(token);
            //从redis里获取权限列表信息
            List<String> permissionValueList = (List<String>)redisTemplate.opsForValue().get(username);

            Collection<GrantedAuthority> authority = new ArrayList<>();
            for (String permissionValue : permissionValueList) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionValue);
                authority.add(simpleGrantedAuthority);
            }
            return new UsernamePasswordAuthenticationToken(username, token, authority);
        }
        return null;
    }
}
