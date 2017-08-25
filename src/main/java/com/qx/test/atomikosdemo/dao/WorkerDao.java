package com.qx.test.atomikosdemo.dao;

import java.util.Map;

/**
 * Created by qinxue on 2017/8/24.
 */
public interface WorkerDao {
    public int insertWorkerFromStudent(String sql, Map<String, Object> conditionParams);
}
