package com.example.demo.model.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.example.demo.model.CompanyVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sxmzv on 2018/11/28.
 */
@Data
public class UserEX implements Serializable {

    @Excel(name = "用户名", orderNum = "0" )
    private String userName;

    @Excel(name = "年龄", orderNum = "1" )
    private Integer age;

    @Excel(name = "生日", orderNum = "2" )
    private Date birthDay;

    @Excel(name = "公司", orderNum = "3" )
    private Long companyId;

    @Excel(name = "薪资", orderNum = "4" )
    private Integer salary;
}
