package com.qx.test.atomikosdemo.dao.impl;

import com.qx.test.atomikosdemo.bean.Worker;
import com.qx.test.atomikosdemo.dao.WorkerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinxue on 2017/8/24.
 */
@Repository
public class WorkerDaoImpl implements WorkerDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int insertWorkerFromStudent(String sql, Map<String, Object> conditionParams) {

        return jdbcTemplate.update(sql, conditionParams);
    }
}
