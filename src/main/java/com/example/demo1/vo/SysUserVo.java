package com.example.demo1.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class SysUserVo {

    @ApiModelProperty(value = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String userPhone;
}
