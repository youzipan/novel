package com.example.demo;

import com.jeesuite.scheduler.AbstractJob;
import com.jeesuite.scheduler.JobContext;
import com.jeesuite.scheduler.annotation.ScheduleConf;
import org.springframework.stereotype.Service;

/**
 * @author Fengping.Pan
 * @Description:
 */
@Service
@ScheduleConf(cronExpr="0/2 * * * * ?",jobName="demoTask",executeOnStarted = true)
public class DemoTask extends AbstractJob {

    int count = 1;
    @Override
    public void doJob(JobContext context) throws Exception {
        System.out.println(System.currentTimeMillis());
    }

    @Override
    public boolean parallelEnabled() {
        // 分布式下开启并行计算返回true
        return false;
    }

}