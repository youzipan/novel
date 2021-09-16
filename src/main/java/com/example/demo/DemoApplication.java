package com.example.demo;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.jeesuite.springboot.starter.scheduler.EnableJeesuiteSchedule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = { "com.sqltoy.config", "com.sqltoy.quickstart", "com.example.demo" })
@EnableTransactionManagement
@RestController
@EnableJeesuiteSchedule
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	// 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
	@RequestMapping("doLogin")
	public String doLogin(String username, String password) {
		// 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
		if("zhang".equals(username) && "123456".equals(password)) {
			StpUtil.login(10001);
			return "登录成功";
		}
		return "登录失败";
	}

	// 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
	@RequestMapping("isLogin")
	public String isLogin(String username, String password) {
		return "当前会话是否登录：" + StpUtil.isLogin();
	}
	@SaCheckLogin
	@RequestMapping("t")
	public String t() {
		StpUtil.hasPermission("user-update");
		return "当前会话是否登录：" + StpUtil.hasPermission("user-update");
	}
	@RequestMapping("te")
	public String te() {
		StpUtil.hasPermission("user-update1");
		return "当前会话是否登录：" + StpUtil.hasPermission("user-update1");
	}

}
