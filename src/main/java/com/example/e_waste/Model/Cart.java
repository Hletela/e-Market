package com.example.e_waste.Model;

public class Cart
{

    private String pid, pname, price;

    public Cart() {
    }

    public Cart(String pid, String pname, String price) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
