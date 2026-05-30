package com.cmbccd.ulms.common.exception;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmbccd.ulms.sys.domain.Msg;
/**
 * 
* <p>Title: MainsiteErrorController.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@RestController
public class MainsiteErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @Resource
    ErrorAttributes errorAttributes;
    
    @RequestMapping(
            value = {ERROR_PATH},
            produces = {"text/html"}
    )
    public Msg errorHtml(HttpServletRequest request, HttpServletResponse response) {
        int code = response.getStatus();
        if (404 == code) {
            return new Msg(404, "没有找到该页面");
        } else if (403 == code) {
            return new Msg(403, "没有权限，请联系管理员获取相关的权限后再次尝试访问该页面！");
        } else if (401 == code) {
            return new Msg(401, "用户没有登录，请重新登录！");
        } else {
            return new Msg(500, "服务器应用处理异常，请联系管理员！");
        }

    }

    @RequestMapping(value = ERROR_PATH)
    public Msg handleError(HttpServletRequest request, HttpServletResponse response) {
        Integer code = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        if (code == null) {
            code = 500;
        }
        if (404 == code) {
            return new Msg(404, "未找到资源");
        } else if (403 == code) {
            return new Msg(403, "没有访问权限");
        } else if (401 == code) {
            return new Msg(401, "登录过期");
        } else {
            return new Msg(500, "服务器错误");
        }
    }
}