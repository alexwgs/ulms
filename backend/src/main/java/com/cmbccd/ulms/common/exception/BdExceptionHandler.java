package com.cmbccd.ulms.common.exception;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import cn.dev33.satoken.exception.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cmbccd.ulms.sys.domain.ErrorLog;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.ErrorLogService;

/**
 * 
* <p>Title: BDExceptionHandler.java</p>  
* <p>Description: 异常处理器</p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@RestControllerAdvice
public class BdExceptionHandler {
	
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    ErrorLogService errorLogService;

    /**
     * @param e
     * @return
     */
    @ExceptionHandler(BdException.class)
    public Msg handleBdException(BdException e) {
        logger.error(e.getMessage(), e);
        errorLogService.insertNewErrorLog(ErrorLog.error("DataBase", "BDException(未知异常)", e.getMessage()));
        return Msg.error("未知数据库异常！");
    }
    
    @ExceptionHandler(NullPointerException.class)
    public Msg nullPointerExceptionHandler(NullPointerException e) {
        errorLogService.insertNewErrorLog(ErrorLog.error("Class", "NullPointerException（空指针异常）", e.getMessage()));
        logger.error(e.getMessage(), e);
        return new Msg(500,"空指针异常！");
    }
    
    @ExceptionHandler(DuplicateKeyException.class)
    public Msg handleDuplicateKeyException(DuplicateKeyException e) {
        errorLogService.insertNewErrorLog(ErrorLog.error("DataBase", "DuplicateKeyException（数据库中已存在该记录）", e.getMessage()));
        logger.error(e.getMessage(), e);
        return Msg.error("数据库中已存在该记录！");
    }

    @ExceptionHandler(DataAccessException.class)
    public Msg sqlExceptionHandle(DataAccessException e, HttpServletRequest request) {
        errorLogService.insertNewErrorLog(ErrorLog.error("DataBase", "DataAccessException（SQL异常）", e.getMessage()));
    	logger.error(e.getMessage(), e);
        return Msg.error("您提交的数据异常，请检查表单或操作！");
    }
    
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public Msg noHandlerFoundException(org.springframework.web.servlet.NoHandlerFoundException e) {
        errorLogService.insertNewErrorLog(ErrorLog.error("Controller", "NoHandlerFoundException（没有找到处理路径）", e.getMessage()));
    	logger.error(e.getMessage(), e);
        return new Msg(404, "没找找到页面！");
    }
    
    @ExceptionHandler(NumberFormatException.class)
    public Msg numberFormatExceptionHandler(NumberFormatException e) {
        errorLogService.insertNewErrorLog(ErrorLog.warning("Controller", "NumberFormatException（数字类型异常）", e.getMessage()));
        logger.warn(e.getMessage(), e);
        return Msg.error("数字类型异常！");
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Msg handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
    	logger.warn(e.getMessage(), e);
        errorLogService.insertNewErrorLog(ErrorLog.warning("Controller", "MethodArgumentNotValidException（方法参数异常）", e.getMessage()));
        return  Msg.error("数据请求的参数异常，请检查查询条件！");
    }
    // 拦截：未登录异常
    @ExceptionHandler(NotLoginException.class)
    public Msg handlerException(NotLoginException e, HttpServletResponse response) {
        // 未登录是预期内状态（每个匿名请求都会触发），仅记录简短信息避免堆栈刷屏
        logger.info("未登录访问被拦截: {}", e.getMessage());
        // 设置 HTTP 状态码为 401，以便前端拦截器正确识别
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 返回给前端
        return new Msg(401, "Token已过期或没有登录！");
    }

    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public Msg handlerException(NotPermissionException e) {
        logger.warn(e.getMessage(), e);
        errorLogService.insertNewErrorLog(ErrorLog.warning("Controller", "NotPermissionException（缺少权限）", e.getMessage()));
        return Msg.error("缺少权限：" + e.getPermission());
    }

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public Msg handlerException(NotRoleException e) {
        logger.warn(e.getMessage(), e);
        errorLogService.insertNewErrorLog(ErrorLog.warning("Controller", "NotRoleException（缺少角色）", e.getMessage()));
        return Msg.error("缺少角色：" + e.getRole());
    }

    // 拦截：服务封禁异常
    @ExceptionHandler(DisableServiceException.class)
    public Msg handlerException(DisableServiceException e) {
        logger.warn(e.getMessage(), e);
        errorLogService.insertNewErrorLog(ErrorLog.warning("Controller", "DisableServiceException（服务封禁）", e.getMessage()));
        return Msg.error("当前账号 " + e.getService() + " 服务已被封禁 (level=" + e.getLevel() + ")：" + e.getDisableTime() + "秒后解封");
    }

    // 拦截：Http Basic 校验失败异常
    @ExceptionHandler(NotHttpBasicAuthException.class)
    public Msg handlerException(NotHttpBasicAuthException e) {
        logger.warn(e.getMessage(), e);
        errorLogService.insertNewErrorLog(ErrorLog.warning("Controller", "NotHttpBasicAuthException（Http Basic 校验失败）", e.getMessage()));
        return Msg.error(e.getMessage());
    }

    // 拦截：其它所有异常（不向前端回传内部异常细节，完整堆栈记录到日志与 ErrorLog）
    @ExceptionHandler(Exception.class)
    public Msg handlerException(Exception e) {
        logger.error("未捕获异常", e);
        errorLogService.insertNewErrorLog(ErrorLog.error("Controller", "Exception（其它异常）", e.getMessage()));
        return Msg.error("系统异常，请稍后重试或联系管理员");
    }
}
