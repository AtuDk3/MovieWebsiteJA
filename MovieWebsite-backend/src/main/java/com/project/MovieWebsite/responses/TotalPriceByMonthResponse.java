package com.project.MovieWebsite.responses;

import com.project.MovieWebsite.dtos.TotalPriceByMonthDTO;

import java.util.List;

public class TotalPriceByMonthResponse {
    private List<TotalPriceByMonthDTO> totalPriceByMonth;

    public TotalPriceByMonthResponse(List<TotalPriceByMonthDTO> totalPriceByMonth) {
        this.totalPriceByMonth = totalPriceByMonth;
    }

    public List<TotalPriceByMonthDTO> getTotalPriceByMonth() {
        return totalPriceByMonth;
    }

    public void setTotalPriceByMonth(List<TotalPriceByMonthDTO> totalPriceByMonth) {
        this.totalPriceByMonth = totalPriceByMonth;
    }
}
