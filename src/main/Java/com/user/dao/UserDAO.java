package com.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.user.model.User;

public class UserDAO {
	
	private String jdbcURL="jdbc:mysql://localhost:30006/userappdb";
	private String jdbcUserName="root";
	private String jdbcPassword="root";
	
	private static final String INSERT_USER_SQL="INSERT INTO users"+"(uname,email,country,passwd) VALUES "+"(?,?,?,?);";
	
	private static final String SELECT_USER_BY_ID="SELECT * FROM USERS WHERE ID=?;";//MAYBE A PROBLEM TRY TO MAKE USERS AND ID IN LOWERCASE
	private static final String SELECT_ALL_USER="SELECT * FROM USERS;";  //SAME FOR THIS AS USERS IS NAME OF TABLE
	private static final String DELETE_USER_SQL="DELETE FROM USERS WHERE ID=?;";
	private static final String UPDATE_USER_SQL="UPDATE USERS SET UNAME=?,EMAIL=?,COUNTRY=?,PASSWORD=? WHERE ID=?;";   //IF NOT WORKING TRY TO CHANGE PASSWORD TO PASSWRD
	
	public UserDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Connection getConnection() {
		Connection connection=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
		}
		catch(SQLException | ClassNotFoundException e )
		{
			e.printStackTrace();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return connection;
		
	}
	public void insertUser(User user) {
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection()){
			PreparedStatement preparedStatement=connection.prepareStatement(INSERT_USER_SQL);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setString(4, user.getPassword());
			
			preparedStatement.executeUpdate();

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public User selectUser(int id) {
		User user=new User();
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection()){
			PreparedStatement preparedStatement=connection.prepareStatement(SELECT_USER_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			while (resultSet.next()) {
				user.setId(id);
				user.setName(resultSet.getString("uname"));
				user.setEmail(resultSet.getString("email"));
				user.setCountry(resultSet.getString("country"));
				user.setPassword(resultSet.getString("passwrd"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	public List<User> selectAllUsers(){
		List<User> users=new ArrayList<User>();
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection()){
			PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL_USER);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String uname=resultSet.getString("uname");
				String email=resultSet.getString("email");
				String country=resultSet.getString("country");
				String password=resultSet.getString("passwd");

				users.add(new User(id,uname,email,country,password));
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return users;
		
	}
	
	public boolean deleteUser(int id) {
		boolean status=false;
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection()){
			PreparedStatement preparedStatement=connection.prepareStatement(DELETE_USER_SQL);
			preparedStatement.setInt(1, id);
			status=preparedStatement.execute();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
}
