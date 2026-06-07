package com.cmbccd.ulms.sys.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.AESUtil;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.OperateLog;
import com.cmbccd.ulms.sys.domain.User;
import com.cmbccd.ulms.sys.service.OperateLogService;
import com.cmbccd.ulms.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月25日 下午1:28:01
 * @Version 1.0 Description:
 */
@RestController
@RequestMapping("/")
@CrossOrigin
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private UserService userService;
	@Resource
	private OperateLogService operateLogService;

	@PostMapping(value = "/login")
	public Msg login(@RequestBody User user) throws Exception {
		String decryptPassword = AESUtil.decrypt(user.getPassword());
		User user0 = userService.selectByCzyCode(user.getCzyCode());
		if (Util.isNullorEmpty(user0)) {
			return new Msg(401, "用户不存在");
		}

		if (Util.isNullorEmpty(user0.getPassword())) {
			return new Msg(401, "账户未设置密码，请联系管理员");
		}
		if (decryptPassword.equals(Util.getdesecret(user0.getPassword()))) {
			StpUtil.login(user.getCzyCode());
		} else {
			return new Msg(401, "密码错误");
		}
		SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
		operateLogService.add("LOGIN", "submit");
		return Msg.success("登录成功！欢迎 " + user0.getName() + " 使用A6广场").put("token", tokenInfo.tokenValue).put("userId", user.getCzyCode())
				.put("user", DataCache.EMPLOYEE.get(user.getCzyCode()));
	}

	// test
	@GetMapping("/user/info")
	public Msg getUserInfo() {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return new Msg(401, "Token已过期或没有登录！");
		}
		return Msg.success().put("user", DataCache.EMPLOYEE.get(userId));
	}

	@PostMapping( "/log")
	public Msg newOperateLog(@RequestBody OperateLog record) {
		operateLogService.add(record.getModule(), record.getOperate());
		return Msg.success();
	}

	@PutMapping(value = "/modifyPassword")
	@MyLog(title = "[sys-login]账号管理")
	public Msg modifyPassword(@RequestBody Map<String, String> params) throws Exception {
		String czyCode = params.get("czyCode");
		String oPassword = AESUtil.decrypt(params.get("oPassword"));
		String nPassword = AESUtil.decrypt(params.get("nPassword"));
		String cPassword = AESUtil.decrypt(params.get("cPassword"));
		User user = userService.selectByCzyCode(czyCode);
		if (Util.isNullorEmpty(user)) {
			return Msg.error("用户不存在！");
		}
//		if (Util.isNullorEmpty(user.getPassword())) {
//			return Msg.error("账户未设置密码，无法修改");
//		}
		String sysPassword = Util.getdesecret(user.getPassword());

		if (!oPassword.equals(sysPassword)) {
			return Msg.error("密码验证错误！");
		}

		if (!nPassword.equals(cPassword)) {
			return Msg.error("两次输入的新密码不一致！");
		}
		User newUser = new User();
		newUser.setCzyCode(czyCode);
		String secPassword = Util.getsecret(cPassword);
		newUser.setPassword(secPassword);

		int count = userService.updatePasswordByCzyCode(newUser);
		if (count != 1) {
			return Msg.error("修改密码失败！");
		}
		return Msg.success("密码修改成功！");
	}

	@GetMapping("/logout")
	public String logout() {
		StpUtil.logout();
		logger.info("用户选择退出注销账号！");
		return "index.html";
	}

	@GetMapping("/unauth")
	public Msg unauth() {
		return Msg.warn("登录信息已过期，请重新登录！");
	}

	@GetMapping("/403")
	public Msg appError() {
		return Msg.warn("您没有访问权限!");
	}

	@GetMapping("error/500")
	public Msg aaplicationError() {
		return Msg.error("应用错误!");
	}
}
