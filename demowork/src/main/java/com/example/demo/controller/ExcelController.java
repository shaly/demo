package com.example.demo.controller;

import com.example.demo.model.ResultModel;
import com.example.demo.model.excel.UserEX;
import com.example.demo.service.ExcelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by sxmzv on 2018/11/26.
 */
@RestController
//@RequestMapping("/base")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @RequestMapping(value = "/importUser",method = RequestMethod.POST)
    @ApiOperation(value = "导入用户信息【Jane】 ",notes = "上传用户信息")
    public ResultModel<List<UserEX>> importUser(@ApiParam(name = "uploadFile" ,value = "导入文件") @RequestParam("uploadFile") MultipartFile uploadFile){
        final List<UserEX> listUser = excelService.importUser(uploadFile);
        return new ResultModel<List<UserEX>>(listUser,ResultModel.SUCCESS);
    }

    @RequestMapping(value = "/exportUser",method = RequestMethod.GET)
    @ApiOperation(value = "导出用户信息【Jane】",notes = "下载用户信息")
    public void exportUser(UserEX userEX , HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        excelService.exportUser(response,request,userEX);
    }
}
