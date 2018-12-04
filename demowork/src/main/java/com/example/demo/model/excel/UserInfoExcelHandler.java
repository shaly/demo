package com.example.demo.model.excel;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

/**
 * Created by sxmzv on 2018/11/28.
 */
public class UserInfoExcelHandler extends ExcelDataHandlerDefaultImpl<UserEX>{
    @Override
    public Object importHandler(UserEX userEX,String name,Object value){
        return super.importHandler(userEX,name,value);
    }
}
