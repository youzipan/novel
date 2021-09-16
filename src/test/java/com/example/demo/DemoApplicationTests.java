package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Resource(name = "sqlToyLazyDao")
    private SqlToyLazyDao sqlToyLazyDao;

    @Test
    public void queryStaffInfo() {
        String[] paramNames = { "orderSeq" };
        Object[] paramValue = { "TP16b240ba000002" };
        //最后一个参数是返回类型 null 则返回普通数组(可以传VO对象、Map.class)
        List staffInfo = sqlToyLazyDao.findBySql("qstart_query_staffInfo", paramNames, paramValue, null);
//        Page qstart_query_staffInfo = sqlToyLazyDao.findPageBySql(new Page(2, 2), "qstart_query_staffInfo", paramNames, paramValue);
        System.out.println(staffInfo);
    }
}
