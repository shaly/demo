package com.example.demo.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jane on 2018/11/26.
 */
@Data
@ApiModel(value="用户VO",description="用户导入展示VO")
public class UserVO implements Serializable {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(name = "年龄" )
    private Integer age;

    @ApiModelProperty(name = "生日" )
    private Date birthDay;

    @ApiModelProperty(name = "公司" )
    private Long companyId;

    @ApiModelProperty(name = "薪资" )
    private Integer salary;
}
