package com.example.demo1.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserPageDto {

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户电话")
    private String phone;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;
}
