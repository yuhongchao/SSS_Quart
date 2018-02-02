package com.qf.service.imple;

import com.qf.dao.RankDao;
import com.qf.domain.Rank;
import com.qf.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RankServiceImple implements RankService {
    @Autowired
    private RankDao dao;

    @Override
    public boolean save(Rank rank) {
        return dao.save(rank)!=null;
    }

    @Override
    public boolean update(Rank rank) {
        return dao.save(rank)!=null;
    }

    @Override
    public boolean update(int score, int groupno) {
        return dao.update(score,groupno)>0;
    }

    @Override
    public boolean delete(Long id) {
        try{
            dao.delete(id);
            return  true;
        }catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Page<Rank> getByPage(int page,int size) {
        Pageable pageable=new PageRequest(page-1,size);
        return dao.findAll(pageable);
    }

    @Override
    public Rank getById(Long id) {
        return dao.findOne(id);
    }

    @Override
    public List<Rank> getByIds(Long[] ids) {


        return null;
    }

    @Override
    public List<Rank> selectById(Long id) {
        Specification<Rank> specification=new Specification<Rank>() {
            @Override
            public Predicate toPredicate(Root<Rank> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.gt(root.get("id").as(Long.class),id);
            }
        };
        return dao.findAll(specification);
    }
}
