package com.example.demo.model.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;
import com.example.demo.constant.ErrConstant;
import com.example.demo.exception.GlobalException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by sxmzv on 2018/11/28.
 */
public class ExcelUtils {
    private static final Logger logger= LoggerFactory.getLogger(ExcelUtils.class);

    private static final String MSG_EXPORT="Excel导出失败！错误原因[%s]";

    private static final String MSG_EXPORT_NULL_FILE="Excel导入失败！错误原因[excel文件不能为空]";

    private static final String MSG_IMPORT="Excel导入失败！错误原因[%s]";

    /**
     * Map集合统一导出接口
     * @param list
     * @param fileName
     * @param response
     */
    public static void exportMapExcel(List<Map<String,Object>> list , String fileName, HttpServletResponse response){
        defaultExportMapList(list,fileName,response);
    }

    /**
     * 类集合统一导出接口
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param response
     */
    public static void exportClassExcel(List<?> list,String title,String sheetName,Class<?> pojoClass,String fileName,HttpServletResponse response){
        defaultExportClassList(list,pojoClass,fileName,response,new ExportParams(title,sheetName));
    }

    /**
     * 导出Map集合数据EXCEL
     * @param list
     * @param fileName
     * @param response
     */
    private static void defaultExportMapList(List<Map<String,Object>> list,String fileName,HttpServletResponse response){
        Workbook workbook=ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if(workbook!=null)
            downLoadExcel(fileName,response,workbook);
    }

    /**
     * 导出类集合数据EXCEL
     * @param list
     * @param pojoClass
     * @param fileName
     * @param response
     * @param exportParams
     */
    private static void defaultExportClassList(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams){
        Workbook workbook= ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if(workbook!=null)
            downLoadExcel(fileName,response,workbook);
    }
    /**
     * 输出流输出
     * @param fileName
     * @param response
     * @param workbook
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook){
        logger.info("导出文件名称：【"+fileName+"】");
        try {
            ServletOutputStream outputStream=response.getOutputStream();
            response.setHeader("Content-disposition",fileName);
            //eg.1 解决中文乱码问题【前端获取流之后需要进行decode更改文件名】
            //application/vnd.ms-ExampleExcelHandler
            //response.setContentType("application/vnd.ms-ExampleExcelHandler");

            //eg.2 英文无乱码问题
            //application/vnd.ms-excel
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition","attachment;fileName="+fileName);

            response.setHeader("Pragma","No-cache");
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new GlobalException(ErrConstant.CODE_EXPORT,String.format(MSG_EXPORT,e.getMessage()));
        }
    }

    /**
     * 设置excel表格导出响应头与文件名乱码处理
     * @param response
     * @param request
     * @param fileName
     */
    public static void responseExport(HttpServletResponse response, HttpServletRequest request,String fileName) {
        response.setHeader("content-Type", "application/vnd.ms-excel");
        String agent = request.getHeader("user-agent");
        try {
            if (agent.contains("Firefox")) {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }else {
                fileName= URLEncoder.encode(fileName,"utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xls");
        response.setCharacterEncoding("UTF-8");
    }

    /**
     * 文件名encode
     * @param fileName
     * @param agent
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeDownloadFileName(String fileName,String agent) throws UnsupportedEncodingException {
        if (agent.contains("Firefox")){
            fileName="=?UTF-8?B?"+new BASE64Encoder().encode(fileName.getBytes("utf-8"))+"?=";
        }else {
            fileName=URLEncoder.encode(fileName,"utf-8");
        }
        return fileName;
    }

    /**
     * 导入文件，返回对象集合
     * @param file
     * @param title
     * @param pojoClass
     * @param iExcelDataHandler
     * @param <T>
     * @return
     */
    public static <T>List<T> importExcel(MultipartFile file, String [] title, Class<T> pojoClass, IExcelDataHandler<T> iExcelDataHandler){
        if(file==null){
            return null;
        }
        ImportParams importParams=new ImportParams();
        //数据处理
        iExcelDataHandler.setNeedHandlerFields(title);// 类中指定的列名。
        importParams.setDataHanlder(iExcelDataHandler);
        //需要验证
        importParams.setNeedVerfiy(true);
        List<T> list=null;
        try {
            list= ExcelImportUtil.importExcel(file.getInputStream(),pojoClass,importParams);
        }catch (NoSuchElementException e){
            throw new GlobalException(ErrConstant.CODE_IMPORT , MSG_EXPORT_NULL_FILE);
        }  catch (Exception e) {
            throw new GlobalException(ErrConstant.CODE_GLOBAL , String.format(MSG_IMPORT,e.getMessage()));
        }
        return list;
    }

}
