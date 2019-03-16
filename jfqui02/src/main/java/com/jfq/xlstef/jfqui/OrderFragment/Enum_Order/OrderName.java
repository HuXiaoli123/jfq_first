package com.jfq.xlstef.jfqui.OrderFragment.Enum_Order;

public   enum OrderName
{

    CompleteOrder("CompleteOrder"),
    ShopMallOrder("2"),
    SweepCode("3"),
    DetailCommission("4"),
    DailyOrder("5"),
    UnpayOrder("6");

    private String type;

    OrderName(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
