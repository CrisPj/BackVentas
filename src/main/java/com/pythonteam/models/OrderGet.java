package com.pythonteam.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrderGet {
    private int orderid;
    private int customerId;
    private int employeeId;
    private boolean status;
    @ColumnName("orderdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderdate;

    @ColumnName("completeddate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate completeddate;


    public LocalDate getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }

    private ArrayList<Product> productList = new ArrayList<>();;

    public int getEmployeeId() {
        return employeeId;
    }

    public void addProduct(Product product)
    {
        productList.add(product);
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public int getOrderId() {
        return orderid;
    }

    public void setOrderId(int orderid) {
        this.orderid = orderid;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getCompleteddate() {
        return completeddate;
    }

    public void setCompleteddate(LocalDate completeddate) {
        this.completeddate = completeddate;
    }
}
