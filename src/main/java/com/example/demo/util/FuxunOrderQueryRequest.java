package com.example.demo.util;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: FuxunOrderQueryRequest
 * @ProjectName middle-ground
 * @Description: 订单详情查询入参
 * @author Fengping.Pan
 * @date 2021/9/6 10:38
 */
public class FuxunOrderQueryRequest implements Serializable{

    /**
     * 查询订单参数
     */
    private List<String> reservationNumbers;

    public List<String> getReservationNumbers() {
        return reservationNumbers;
    }

    public void setReservationNumbers(List<String> reservationNumbers) {
        this.reservationNumbers = reservationNumbers;
    }
}
