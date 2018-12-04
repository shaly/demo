package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Jane on 2018/11/26.
 */
@Data
@ApiModel(value="用户VO",description="用户导入展示VO")
public class CompanyVO implements Serializable{

    @ApiModelProperty(value = "公司名")
    private String name;

    @ApiModelProperty(value = "公司地址")
    private String address;

}
