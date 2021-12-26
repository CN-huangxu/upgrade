package com.example.demo1.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo1.entity.SysUser;
import com.example.demo1.mapper.SysUserMapper;
import com.example.demo1.security.JwtUtils;
import com.example.demo1.security.UserDetail;
import com.example.demo1.service.ISysUserService;
import com.example.demo1.utils.CommonException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author weikangdi
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final JwtUtils jwtUtils;

    @Override
    public JSONObject login(String username, String password) {
        SysUser sysUser = lambdaQuery().eq(SysUser::getName, username)
                .eq(SysUser::getPassword, password).one();
        if (sysUser == null) {
            throw new CommonException("用户名密码错误");
        }
        UserDetail userDetail = UserDetail.parse(sysUser);
        String token = jwtUtils.generateToken(userDetail);
        sysUser.setLastLoginTime(new Date());
        saveOrUpdate(sysUser);
        return getUserJson(userDetail, token);
    }

    private JSONObject getUserJson(UserDetail userDetail, String token) {
        JSONObject params = new JSONObject();
        params.put("token", token);
        params.put("userName", userDetail.getUsername());
        return params;
    }
}
