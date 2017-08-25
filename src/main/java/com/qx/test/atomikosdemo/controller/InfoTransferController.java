package com.qx.test.atomikosdemo.controller;

import com.alibaba.fastjson.JSON;
import com.qx.test.atomikosdemo.service.InfoTransferService;
import com.qx.test.atomikosdemo.util.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qinxue on 2017/8/24.
 */
@RestController
@RequestMapping("/atomikos/demo")
@Slf4j
@Api(value = "InfoTransferController", description = "提供信息跨库转表服务")
public class InfoTransferController {

    @Autowired
    private InfoTransferService infoTransferService;

    @RequestMapping(value = "/student/stuname/{stuname}/stuaddr/{stuaddr}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据学名称和学生地址更改该学生地址并将学生信息跨库转换到另一表中",
            notes = "根据学名称和学生地址更改该学生地址并将信息转换到另一表中", response = Message.class)
    @ApiResponses(value = {@ApiResponse(code = 401, message = "服务器认证失败"),
            @ApiResponse(code = 403, message = "资源不存在"),
            @ApiResponse(code = 404, message = "传入的参数无效"),
            @ApiResponse(code = 500, message = "服务器出现异常错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stuname", value = "学生名称", required = true,
                    dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "stuaddr", value = "学生地址", required = true,
                    dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "stuid", value = "上下文信息",
                    dataType = "String", paramType = "query")
    })
    public String transferInfoByParams(@PathVariable("stuname") String stuName,
                                       @PathVariable("stuaddr") String stuAddr,
                                       @RequestParam(value = "stuid", required = false) String stuId) {
        if (stuId != null) {
            log.info("===========>>> stuId: " + stuId);
        }
        log.info("============>>> stuName: " + stuName + " --- stuAddr: " + stuAddr);
        int i = infoTransferService.transStudentToWorker(stuName, stuAddr);
        Message message = new Message();
        if (i > 0) {
            message.setRspCode("0000");
            message.setResDesc("transfer success");
        } else {
            message.setRspCode("8888");
            message.setResDesc("transfer failure");
        }
        log.info("=============>>> returned message: " + JSON.toJSONString(message));
        return JSON.toJSONString(message);
    }
}
