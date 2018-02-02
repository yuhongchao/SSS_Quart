package com.qf.dao;

import com.qf.domain.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankDao extends JpaRepository<Rank,Long>,JpaSpecificationExecutor<Rank> {

    //修改条件为组号  修改分数   JPQL
    @Modifying
    @Query("update Rank set score=?1 where groupNo=?2")
    public int update(int score,int gno);

    //查询 条件zuhao--SQL
    @Query(value="select * from t_rank where groupNo=?1",nativeQuery=true)
    public Rank selectByGno(int gno);

    //查询--方法名  模糊   项目
    public List<Rank> getByProjectNameLike(String pname);

    //动态查询---







}
