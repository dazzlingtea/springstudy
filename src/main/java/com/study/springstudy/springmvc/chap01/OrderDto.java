package com.study.springstudy.springmvc.chap01;

public class OrderDto {
    private int orderNum;
    private String goods;
    private int amount;
    private int price;

    // setter getter 반드시 룰에 맞게 생성


    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderNum=" + orderNum +
                ", goods='" + goods + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
