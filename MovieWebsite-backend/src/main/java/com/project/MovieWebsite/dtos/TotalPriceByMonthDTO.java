package com.project.MovieWebsite.dtos;

public class TotalPriceByMonthDTO {
    private int month;
    private Double totalPrice;

    public TotalPriceByMonthDTO(int month, Double totalPrice) {
        this.month = month;
        this.totalPrice = totalPrice;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
