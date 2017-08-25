package com.qx.test.atomikosdemo.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by qinxue on 2017/8/24.
 */
@Data
@NoArgsConstructor
public class Student {
    private String studentId;
    private String studentName;
    private String studentAddr;

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentAddr='" + studentAddr + '\'' +
                '}';
    }
}
