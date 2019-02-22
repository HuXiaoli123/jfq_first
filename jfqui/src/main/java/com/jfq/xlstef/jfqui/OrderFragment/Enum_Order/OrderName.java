package com.jfq.xlstef.jfqui.OrderFragment.Enum_Order;

public   enum OrderName
{

    SweepCode("1"),
    AddCountOrder("2"),
    ComdityOrder("3"),
    CommissionEntry("4");

    private String type;

    OrderName(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
