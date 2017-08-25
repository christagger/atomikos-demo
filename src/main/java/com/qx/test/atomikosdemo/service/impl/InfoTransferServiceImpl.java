package com.qx.test.atomikosdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.qx.test.atomikosdemo.bean.Student;
import com.qx.test.atomikosdemo.config.datasource.DynamicDataSourceContextHolder;
import com.qx.test.atomikosdemo.dao.StudentDao;
import com.qx.test.atomikosdemo.dao.WorkerDao;
import com.qx.test.atomikosdemo.service.InfoTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinxue on 2017/8/24.
 */
@Service
@Slf4j
public class InfoTransferServiceImpl implements InfoTransferService {

    @Autowired
    private UserTransaction userTransaction;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private WorkerDao workerDao;

    @Override
    public int transStudentToWorker(String stuName, String stuAddr) {

        int affectedLines = 0;
        try {
            //if no datasource secified, use the defaultTargetDataSource ds2
//            DynamicDataSourceContextHolder.set("ds2");
            String sql = "SELECT * FROM T_STUDENT WHERE STUDENT_NAME = :VSTUDENT_NAME AND STUDENT_ADDR = :VSTUDENT_ADDR";
            Map<String, Object> conditionParams = new HashMap<>();
            conditionParams.put("VSTUDENT_NAME", stuName);
            conditionParams.put("VSTUDENT_ADDR", stuAddr);
            Student student = studentDao.queryStudentByNameAndAddr(sql, conditionParams);
            log.info("==========================>>> query student: " + JSON.toJSONString(student));
            if (student == null) {
                throw new RuntimeException("no student found in table t_student");
            }

            userTransaction.begin();
            //change student_addr of student
            student.setStudentAddr(student.getStudentAddr()+"_NEW ADDRESS");
            sql = "UPDATE t_student s SET s.STUDENT_ADDR = :VSTUDENT_ADDR WHERE s.STUDENT_NAME = :VSTUDENT_NAME";
            conditionParams.clear();
            conditionParams.put("VSTUDENT_ADDR", student.getStudentAddr());
            conditionParams.put("VSTUDENT_NAME", student.getStudentName());
            int reslut = studentDao.updateStudentAddr(sql, conditionParams);
            if(reslut > 0) {
              log.info("=========================>>> update t_student table success");
            }

            //change datasource
            DynamicDataSourceContextHolder.set("ds1");
            conditionParams.clear();
            sql = "INSERT t_worker(WORKER_ID, WORKER_NAME, WORKER_ADDR) VALUES(:VWORKER_ID, :VWORKER_NAME, :VWORKER_ADDR)";
            conditionParams.put("VWORKER_ID", "222" + student.getStudentId());
            conditionParams.put("VWORKER_NAME", student.getStudentName());
            conditionParams.put("VWORKER_ADDR", student.getStudentAddr());
            affectedLines = workerDao.insertWorkerFromStudent(sql, conditionParams);
            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                userTransaction.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }
        return affectedLines;

    }
}
