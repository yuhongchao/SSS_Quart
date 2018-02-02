package com.qf.test;

import com.qf.service.RankService;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import sun.applet.Main;

import javax.sound.midi.Soundbank;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//定时任务
public class Quartz_Test {
    public static void main(String[] args) throws SchedulerException {
       test4();

    }
   //Quartz的初体验  --
    private static void helloQuartz() throws SchedulerException {


        //获取计划对象
        Scheduler scheduler= StdSchedulerFactory.getDefaultScheduler();
        //触发条件---现在开始，每秒重复一次
        Trigger trigger= TriggerBuilder.newTrigger().startNow().withIdentity("tri1","grop1").
                withSchedule(
                SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

        //作业详情
        JobDetail jobDetail=JobBuilder.newJob(Hello.class).build();
        //将触发条件和作业添加到计划中
        scheduler.scheduleJob(jobDetail,trigger);
        //开启计划
        scheduler.start();
    }
    //java自带的定时器
    private static void java(){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("醒醒");
            }
        },0,2000);

    }
    //SimpleTrigger的使用
    private static void simple() throws SchedulerException {
        //创建调度器
        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();
        //Lambda表达式实现Job接口
        Job job= (JobExecutionContext jx)->{
            System.out.println("OK");
        };
        //触发器
        SimpleTriggerImpl trigger=new SimpleTriggerImpl();
        trigger.setRepeatCount(6);
        trigger.setStartTime(new Date());
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MINUTE,3);
        trigger.setEndTime(calendar.getTime());
        trigger.setRepeatInterval(20);
       //添加调度器
        scheduler.scheduleJob(JobBuilder.newJob(job.getClass()).build(),trigger);
        scheduler.start();
    }
    //Cron表达式
    public static void cron() throws SchedulerException {
        //创建调度器
        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();
        //作业
        /*Job job=new Job() {
            @Override
            public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
                      //jobExecutionContext.getJobDetail()
                System.out.println("明日竞赛，结果不重要");
            }
        };*/
        //触发器---基于Cron表达式
        //Trigger trigger=TriggerBuilder.newTrigger().build();
        //每天下午的4-5点每隔5s执行一次
        CronTrigger cronTrigger=TriggerBuilder.newTrigger().withIdentity("cron1","grup1").
                withSchedule(CronScheduleBuilder.cronSchedule("0/5 * 16-17 * * ?")).build();
        //将作业添加到调度器中
        scheduler.scheduleJob(JobBuilder.newJob(JsJob.class).build(),cronTrigger);
        //开始计划
        scheduler.start();
        System.out.println("计划已经开启");

    }
    //传输对象
    private static void test4() throws SchedulerException {
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring-dao.xml");
        RankService service=context.getBean(RankService.class);
        JobDetail jobDetail=JobBuilder.newJob(RankJob.class).build();
        //传递
        JobDataMap dataMap=jobDetail.getJobDataMap();
        dataMap.put("service",service);
        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();
        CronTrigger cronTrigger=TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("20 * * * * ?")).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
        scheduler.start();
    }
}
