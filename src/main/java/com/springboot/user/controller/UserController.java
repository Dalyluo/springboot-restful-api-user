package com.springboot.user.controller;

import java.util.*;

import com.springboot.user.util.DecodeUtil;
import com.springboot.user.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springboot.user.model.User;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/user")
public class UserController {
	
	//线程安全Map对象
	static  Map<String, User> users = Collections.synchronizedMap(new HashMap<String,User>());

	@Autowired
	private HttpServletRequest request;
	
	static{
		users.put("1", new User("1","蒋婷婷","18"));
		users.put("2", new User("2","油腻大叔","35"));
	}
	
	@ApiOperation(value="获取用户列表",notes="")
	@RequestMapping(value="/",method=RequestMethod.GET)
	public List<User> getUserList(){
		List<User> data = new ArrayList<User>(users.values()); 
		return data;
	}
	
	@ApiOperation(value="创建用户",notes="")
	@ApiImplicitParam(value="用户详细实体user",name="user",paramType="com.springboot.user.model.User",required=true)
	@RequestMapping(value="/",method=RequestMethod.POST)
	public String putUser(@RequestBody User user){
		Enumeration<String> headerNames = request.getHeaderNames();
	    while(headerNames.hasMoreElements()){
	    	String key =  headerNames.nextElement();
	    	String value = request.getHeader(key);
	    	System.out.println(key+":"+value);
		}
		String referer = request.getHeader("referer");
	    System.out.println("referer:"+referer);

	    System.out.println("ip:"+RequestUtils.getIpAddress(request));
	    System.out.println("remoteHost:"+RequestUtils.getRemoteHost(request));
		System.out.println("remotePort:"+RequestUtils.getRemotePort(request));


		System.out.println("actionCode: "+request.getHeader("ActionCode"));
		String m = DecodeUtil.decrypt(request.getHeader("ActionCode"),"C07AFE941CE8C2DE");
		System.out.println(m);

		if(user==null) return "fail";
		users.put(user.getId(), user);
		return "success";
	}
	
	@ApiOperation(value="获取用户对象",notes="根据用户ID查询用户对象")
	@ApiImplicitParam(value="用户ID",name="id",dataType="String",required=true)
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public User getUserById(@PathVariable String id){
		// 处理"/users/{id}"的GET请求，用来获取url中id值的User信息 
        // url中的id可通过@PathVariable绑定到函数的参数中
		return users.get(id);
	}
	
	
	@ApiOperation(value="更新用户",notes="根据用户ID更新用户信息")
	@RequestMapping(value="/{id}")
	@ApiImplicitParams({
		@ApiImplicitParam(value="用户ID",name="id",dataType="String",required=true),
		@ApiImplicitParam(value="用户详细实体user",name="user",dataType="com.springboot.user.model.User",required=true)
	})
	public String updateUserById(@PathVariable String id, @ModelAttribute User user){
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数 
		if(user==null) return "fail";
	    User u = users.get(id);
	    u.setAge(user.getAge());
	    u.setName(user.getName());
	    users.put(id, u);
		return "success";
	}
	
	@ApiOperation(value="删除用户",notes="根据用户ID删除用户信息")
	@ApiImplicitParam(value="用户ID",name="id",dataType="String",required=true)
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delUserById(@PathVariable String id){
		if(id==null) return "fail";
		users.remove(id);
		return "success";
	}
}
