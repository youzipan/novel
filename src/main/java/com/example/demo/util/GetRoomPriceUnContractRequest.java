package com.example.demo.util;

import java.io.Serializable;


public class GetRoomPriceUnContractRequest implements Serializable{

    /**
     * 请求查询的酒店ID列表
     */
    private Long hotelId;
    /**
     * 请求的协议客户ID
     */
    private String clientId;

    /**
     * 入住日期，格式为yyyy-MM-dd，不能早于当前日期
     */
    private String checkInAt;
    /**
     * 离店日期，格式为yyyy-MM-dd，与当前日期相差不能超过30天
     */
    private String checkOutAt;

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCheckInAt() {
        return checkInAt;
    }

    public void setCheckInAt(String checkInAt) {
        this.checkInAt = checkInAt;
    }

    public String getCheckOutAt() {
        return checkOutAt;
    }

    public void setCheckOutAt(String checkOutAt) {
        this.checkOutAt = checkOutAt;
    }
}
