package com.example.demo.service;

import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;
import com.example.demo.constant.ExcelConstant;
import com.example.demo.model.excel.ExcelUtils;
import com.example.demo.model.excel.UserEX;
import com.example.demo.model.excel.UserInfoExcelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxmzv on 2018/11/26.
 */
@Service
public class ExcelService {

    private Logger logger = LoggerFactory.getLogger(ExcelService.class);
    private static final String EXCEL_USER_NAME="用户信息详情列表";
    /**
     * 导入用户信息
     * @param uploadFile
     * @return
     */
    public List<UserEX> importUser( MultipartFile uploadFile){
        logger.info("================= Start to importUser");
        IExcelDataHandler<UserEX> handler=new UserInfoExcelHandler();
        List<UserEX> userList = ExcelUtils.importExcel(uploadFile, ExcelConstant.USER_EXCEL_IMPORT_TITLE, UserEX.class, handler);
        logger.info("================= Success to importUser ");
        for (UserEX u :userList){
            logger.info(u.getUserName()+"--"+u.getSalary()+"--"+u.getAge());
        }
        return userList;
    }

    /**
     * 导出用户信息
     * @param response
     * @param request
     * @param userEX
     */
    public void exportUser(HttpServletResponse response, HttpServletRequest request, UserEX userEX) throws UnsupportedEncodingException {
        logger.info("================= Start to trans data");
        List<UserEX> userList=new ArrayList<UserEX>();
        UserEX u=null;
        for (int i=0;i<100;i++){
            u=new UserEX();
            BeanUtils.copyProperties(userEX,u);
            u.setSalary(10000+i);
            userList.add(u);
        }
        logger.info("================= End to trans data");
        try {
            logger.info("================= Start to exportUser:"+request.getHeader("user-agent"));
            //eg.1 解决文件名乱码问题
            String fileName = ExcelUtils.encodeDownloadFileName(EXCEL_USER_NAME, request.getHeader("user-agent"));
           // ExcelUtils.exportClassExcel(userList,null,EXCEL_USER_NAME,UserEX.class,fileName+".xls",response);

            //eg.2 英文文件名不会造成乱码
            ExcelUtils.exportClassExcel(userList,null,"user",UserEX.class,"user.xls",response);
            logger.info("================= Success to exportUser");
        } catch (UnsupportedEncodingException e) {
            logger.info("================= Start to exportUser");
            //eg.1 解决文件名乱码问题
            //  String fileName = ExcelUtils.encodeDownloadFileName(EXCEL_USER_NAME, request.getHeader("user-agent"));
            // ExcelUtils.exportClassExcel(new ArrayList<UserEX>(),null,EXCEL_USER_NAME,UserEX.class,fileName+".xls",response);

            //eg.2 英文文件名不会造成乱码
            ExcelUtils.exportClassExcel(new ArrayList<UserEX>(),null,"user",UserEX.class,"user.xls",response);
            logger.info("================= Success to exportUser");
        }

    }

}
