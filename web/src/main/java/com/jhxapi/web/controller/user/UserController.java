package com.jhxapi.web.controller.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@Autowired
	private DataSource dataSource;
	
	@RequestMapping("login")
	public String login(HttpServletRequest request) {
		request.getSession();
		return "user/login";
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public String insert() throws SQLException {
		Connection connection = null;
		Statement createStatement = null;
		try {
			connection = dataSource.getConnection();
			createStatement = connection.createStatement();
			
			long nextLong = (long)(Math.random() * 10000);
			
			createStatement.execute("INSERT INTO `user`(`id`, `city`, `name`) VALUES ("+ nextLong +", 'test', 'test')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(createStatement != null)
				createStatement.close();
			
			if(connection != null) 
				connection.close();
		}
		return "ok";
	}
	
	@RequestMapping("select")
	@ResponseBody
	public String select() throws SQLException, JSONException {
		Connection connection = null;
		Statement createStatement = null;
		
		JSONArray res = new JSONArray();
		try {
			connection = dataSource.getConnection();
			createStatement = connection.createStatement();
			
			ResultSet executeQuery = createStatement.executeQuery("select * from user");
			
			while(executeQuery.next()) {
				long id = executeQuery.getLong("id");
				String city = executeQuery.getString("city");
				String name = executeQuery.getString("name");
				
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.put("id", id);
				jsonObject.put("city", city);
				jsonObject.put("name", name);
				
				res.put(jsonObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(createStatement != null)
				createStatement.close();
			
			if(connection != null) 
				connection.close();
		}
		return res.toString();
	}
	
	@RequestMapping("select1")
	@ResponseBody
	public String select1() throws SQLException, JSONException {
		Connection connection = null;
		Statement createStatement = null;
		
		JSONArray res = new JSONArray();
		try {
			connection = dataSource.getConnection();
			createStatement = connection.createStatement();
			
			ResultSet executeQuery = createStatement.executeQuery("select * from user where id=8951");
			
			while(executeQuery.next()) {
				long id = executeQuery.getLong("id");
				String city = executeQuery.getString("city");
				String name = executeQuery.getString("name");
				
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.put("id", id);
				jsonObject.put("city", city);
				jsonObject.put("name", name);
				
				res.put(jsonObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(createStatement != null)
				createStatement.close();
			
			if(connection != null) 
				connection.close();
		}
		return res.toString();
	}

}
