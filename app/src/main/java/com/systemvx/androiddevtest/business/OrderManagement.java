package com.systemvx.androiddevtest.business;

import com.systemvx.androiddevtest.data.AccountData;
import com.systemvx.androiddevtest.data.OrderData;
import com.systemvx.androiddevtest.data.dao.IOrderDAO;
import com.systemvx.androiddevtest.data.daoImpl.OrderDAOImpl;

public class OrderManagement {
    private OrderData order;
    public void createNewOrder(AccountData publisher,AccountData receiver)
    {
        order.setPublisher(publisher);
        order.setReceiver(receiver);
        IOrderDAO.createOrder(order);
    }


}
