package com.example.demo1;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class LoginUserDto {
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;
}
