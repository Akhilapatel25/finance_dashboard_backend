package com.akhila.finance.DTO;

public class MonthlySummaryDTO {

    private int month;
    private Double total;

    public MonthlySummaryDTO(int month, Double total) {
        this.month = month;
        this.total = total;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}