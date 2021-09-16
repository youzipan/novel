package com.example.demo;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author Fengping.Pan
 */
//@Configuration
public class PageOfficeConfig {

    public static void main(String[] args) {
        System.out.println(DateUtil.endOfDay(new Date()).toDateStr());
    }

}
