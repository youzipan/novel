package com.example.demo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;

/**
 * @author Fengping.Pan
 * @Description:
 */
public class Crawl {
    public static void main(String[] args) {
//        //请求列表页
//        String listContent = HttpUtil.get("https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2");
//
//        //使用正则获取所有标题
//        List<String> titles = ReUtil.findAll("<span class=\"text-ellipsis\">(.*?)</span>", listContent, 1);
//        for (String title : titles) {
//            //打印标题
//            Console.log(title);
//        }
        String dateStr = "2020-01-23T12:23:56";
        DateTime dt = DateUtil.parse(dateStr);

        // Date对象转换为LocalDateTime
        LocalDateTime of = LocalDateTimeUtil.of(dt);

        // 时间戳转换为LocalDateTime
        of = LocalDateTimeUtil.ofUTC(dt.getTime());
        System.out.println(of);

    }
}
