package com.example.demo1.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo1.utils.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        final String token = request.getHeader("Authorization");
        UserDetail userDetail = null;
        if (StringUtils.isNotBlank(token)) {
            userDetail = jwtUtils.getUserDetailFromToken(token);
        }
        if (userDetail != null) {
            try {
                UserDetails userDetails = userDetailsService.loadUser(userDetail, request.getRequestURI());
                if (userDetails.isEnabled()) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetail.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } catch (UsernameNotFoundException e) {
                log.error("username【{}】 not found", userDetail.getUsername(), e);
            }
        } else {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }
}
