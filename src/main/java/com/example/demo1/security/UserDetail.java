package com.example.demo1.security;

import com.example.demo1.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope("session")
public class UserDetail implements UserDetails {

    private Long id;

    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetail parse(SysUser sysUser) {
        return new UserDetail(sysUser.getId(), sysUser.getName());
    }
}
