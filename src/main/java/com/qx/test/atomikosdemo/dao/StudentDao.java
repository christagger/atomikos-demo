package com.qx.test.atomikosdemo.dao;

import com.qx.test.atomikosdemo.bean.Student;

import java.util.Map;

/**
 * Created by qinxue on 2017/8/24.
 */
public interface StudentDao {

    public Student queryStudentByNameAndAddr(String sql, Map<String, Object> conditionParams);
    public int updateStudentAddr(String sql, Map<String, Object> conditionParams);
}
