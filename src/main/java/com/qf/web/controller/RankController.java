package com.qf.web.controller;

import com.qf.common.JsonResult;
import com.qf.domain.Rank;
import com.qf.job.RankJob;
import com.qf.service.QuartzRank;
import com.qf.service.RankService;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.hibernate.annotations.Parameter;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//控制器
@Controller
public class RankController {

    @Autowired
    private RankService service;
    @Autowired
    private QuartzRank quartzRank;
    @RequestMapping("/index.op")
    public String quartz() throws SchedulerException {
        System.out.println("定时器");
        Scheduler scheduler= StdSchedulerFactory.getDefaultScheduler();
        CronTrigger cronTrigger= TriggerBuilder.newTrigger().
                withIdentity("rank1","grouprank").withSchedule(
                        CronScheduleBuilder.cronSchedule("0 0/1 * * * ?")).build();
        JobDetail jobDetail=JobBuilder.newJob(RankJob.class).build();
        jobDetail.getJobDataMap().put("quartzrank",quartzRank);
        scheduler.scheduleJob(jobDetail,cronTrigger);
        scheduler.start();
        return "rank.jsp";
    }
    //新增
    @RequestMapping(value = "rankadd.op",method = {RequestMethod.POST},params = {"score"})
    @ResponseBody
    public JsonResult add(Rank rank){
        if(service.save(rank)){
            return JsonResult.setOK(null);
        }else {
            return JsonResult.setERROR(null);
        }
    }
    //查询
    @RequestMapping(value = "rankpage.op",method = {RequestMethod.GET},params = {"page","size"})
    @ResponseBody
    public JsonResult get(@RequestParam(defaultValue = "1") int page, int size){
        Page<Rank> rankPage= service.getByPage(page, size);
        return  JsonResult.setOK(rankPage);
    }




}
