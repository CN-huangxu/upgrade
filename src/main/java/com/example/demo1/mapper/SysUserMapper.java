package com.example.demo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo1.dto.SysUserPageDto;
import com.example.demo1.entity.SysUser;
import com.example.demo1.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author Administrator
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 分页查询商户信息
     *
     * @param sysUser
     * @param pageRequest
     * @return
     */
    IPage<SysUserVo> pageModel(@Param("page") Page pageRequest, @Param("sysUser") SysUserPageDto sysUser);

}

