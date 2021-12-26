package com.example.demo1.security;

import com.example.demo1.entity.SysUser;
import com.example.demo1.service.ISysUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Administrator
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ISysUserService sysUserService;

    private final String customerUrl = "/api/v1/auth/login";
    private final String cityUrl = "/api/v1/city";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.lambdaQuery().eq(SysUser::getName, username).one();
        return UserDetail.parse(sysUser);
    }

    public UserDetails loadUser(UserDetail userDetail, String url) throws UsernameNotFoundException {
        if (!url.contains(customerUrl) && !url.contains(cityUrl)) {
            throw new UsernameNotFoundException(userDetail.getUsername() + " no authority");
        }

        SysUser sysUser = sysUserService.lambdaQuery().eq(SysUser::getName, userDetail.getUsername()).one();
        if (sysUser == null) {
            throw new UsernameNotFoundException(userDetail.getUsername() + " not found");
        }
        return UserDetail.parse(sysUser);
    }
}
