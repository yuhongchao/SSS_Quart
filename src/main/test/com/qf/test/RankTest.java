package com.qf.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.domain.Rank;
import com.qf.service.RankService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

//测试
public class RankTest {
    @Test
    public void test1() throws JsonProcessingException {
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring-dao.xml");
        RankService service=context.getBean(RankService.class);
        Rank rank=new Rank();
        rank.setGroupNo(5);
        rank.setProjectName("petylu");
        rank.setScore(100);
       // System.out.println("新增："+service.save(rank));
        Page<Rank> page=service.getByPage(1,20);
        System.out.println("总数量："+page.getTotalElements()+"---总页数："+page.getTotalPages());
        System.out.println("内容："+page.getContent());
        ObjectMapper mapper=new ObjectMapper();
        System.out.println("JSON:"+mapper.writeValueAsString(page));

    }
}
