package com.example.demo1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName(value = "sys_user")
public class SysUser {

    /**
     * 主键
     */
    @TableField
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

}
