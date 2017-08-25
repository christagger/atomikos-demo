package com.qx.test.atomikosdemo.dao.impl;

import com.qx.test.atomikosdemo.bean.Student;
import com.qx.test.atomikosdemo.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinxue on 2017/8/24.
 */
@Repository
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    //query student info based on student_name and student_addr
    @Override
    public Student queryStudentByNameAndAddr(String sql, Map<String, Object> conditionParams) {

        List<Student> students = jdbcTemplate.query(sql, conditionParams, new RowMapper<Student>() {

            @Override
            public Student mapRow(ResultSet rs, int i) throws SQLException {
                Student student = new Student();
                student.setStudentId(rs.getString("STUDENT_ID"));
                student.setStudentName(rs.getString("STUDENT_NAME"));
                student.setStudentAddr(rs.getString("STUDENT_ADDR"));
                return student;
            }
        });
        return students.size() > 0 ? students.get(0) : null;
    }

    //update address of specified student
    @Override
    public int updateStudentAddr(String sql, Map<String, Object> conditionParams) {

        return jdbcTemplate.update(sql, conditionParams);
    }
}
