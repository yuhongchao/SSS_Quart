package com.qf.job;

import com.qf.service.QuartzRank;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.sound.midi.Soundbank;

public class RankJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        QuartzRank rank= (QuartzRank) jobExecutionContext.getJobDetail().getJobDataMap().get("quartzrank");
        System.out.println("定时备份："+System.currentTimeMillis());
        rank.barkRank();
        System.out.println("定时备份结束："+System.currentTimeMillis());

    }
}
