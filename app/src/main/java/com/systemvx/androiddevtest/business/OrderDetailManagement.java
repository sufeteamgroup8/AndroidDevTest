package com.systemvx.androiddevtest.business;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.systemvx.androiddevtest.data.DetailData;
import com.systemvx.androiddevtest.data.dao.IOrderDAO;

import java.security.SecureRandom;
import java.util.Date; package daolmpl

public class OrderDetailManagement {
    private DetailData detail;
    public DetailData getOrderDetail(int OrderID)
    {   Boolean version=true;
        detail = IOrderDetailDAO.findorderdetail(OrderID,version);
        //findOrderDetail应该放在orderdetailDAO
        System.out.println(detail);
        //测试性输出，后续应与页面链接
        return detail;
    }


    public void browseOrderVersion(int OrderID)
    {//这个函数的返回类型待修改
        while(IOrderDetailDAO.findorderdetail(OrderID))
        {
            detail = IOrderDetailDAO.findorderdetail(OrderID);//这里需要确认一下返回值，多条记录如何返回
        }
        //我的想法是version给一个默认都可以的值，不指定的情况下遍历数据库把所有该订单相关的记录都找到
    }

    public DetailData OrderDraftQuery(int OrderID)
    {
        String draft= "draft";
        detail = IOrderDetailDAO.findorderdetail(OrderID,draft);
        System.out.println(detail);
        //测试性输出，后续应与页面链接
        return detail;
    }

    public void updateOrderVersion(int OrderDetailID, int OrderID)//orderdetail需要一个key
    {
        detail = IOrderDetailDAO.findorderdetail(OrderID,"new");//找到当前最新版提取
        IOrderDetailDAO.updateOrderVersion(detail.getOrderDetailID(),"old");//更改当前最新版version
        IOrderDetailDAO.updateOrderVersion(OrderDetailID,"new");//更新需要发布的版本version
    }

    public void createOrderDetail(String title,String maintext, double price, int type, int address, String addressStr)
    {
        //这边detail反复使用的安全性存疑，需不需要重新new一个保证空值？
        detail.setAddress(address);
        detail.setAddressStr(addressStr);
        detail.setMaintext(maintext);
        detail.setPrice(price);
        detail.setPubTime(Date());
        detail.setTitle(title);
        detail.setType(type);
        IOrderDetailDAO.newOrderDetail(detail);
    }

}
