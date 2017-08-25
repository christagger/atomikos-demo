package com.qx.test.atomikosdemo.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by qinxue on 2017/8/24.
 */
@Data
@NoArgsConstructor
public class Worker {
    private String workerId;
    private String workerName;
    private String workerAddr;
}
