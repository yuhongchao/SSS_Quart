package com.qf.service.imple;

import com.alibaba.fastjson.JSON;
import com.qf.domain.Rank;
import com.qf.service.QuartzRank;
import com.qf.service.RankService;
import com.qf.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartzRankServiceImple implements QuartzRank {


    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RankService service;
    //存储到list类型--List<String> ranks  需要从数据查询，进行排序，按照主键升许
    @Override
    public void barkRank() {
        List<Rank> list;
        long id=0;
        //1、从redis查询上次备份的最大的主键值
        if(redisUtils.exists("ranks")){
            String json=redisUtils.getLast("ranks");
            if(json.length()>0) {
                Rank rk = JSON.parseObject(json, Rank.class);
                id = rk.getId();
            }
        }
        //2、查询数据库，从上一次备份的最大值开始查询
        list=service.selectById(id);
        if(list.size()>0) {
            //3、将查询的数据保存到Redis中
            for (Rank rank : list) {
                redisUtils.ladd("ranks",JSON.toJSONString(rank));
            }
        }

    }
}
