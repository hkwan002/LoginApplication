package com.optimum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.optimum.connection.DatabaseConnection;
import com.optimum.controller.LoginController;
import com.optimum.pojo.EmailPassword;
import com.optimum.pojo.Login;
import com.optimum.pojo.LoginLogic;
import com.optimum.pojo.User;

public class LoginDAO {
	private static Connection conn = DatabaseConnection.getConnection();	//conn will become the shortcut key to databaseconnection class getconnection method
	static User refUser = new User();
	static Login refLogin = new Login();

	public void checkUser(User refUser) {
		try {		// when user enter email as username in login page pass over will be check if the database have this email inside 
			String query ="SELECT email, status, noofattempts FROM USER WHERE email =?";	
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getEmail());
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {	// if there is result it will set number of attempts and status of that account into the system for later part
				refUser.setNoofattempts(rs.getInt("noofattempts"));
				refUser.setStatus(rs.getBoolean("status"));
				refLogin.loginPage2(refUser);	// pass over to login page 2 to input password
			}
			else {
				System.out.println("Invalid Login ID");
				refLogin.loginPage();		// invalid username will be prompt to re-enter
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void loginAuthentication(User refUser) {
		try {		// check if email and password is correct if correct there will be output with the select statement below
			String query = "SELECT * FROM USER WHERE email =? and password=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getEmail());
			preparedStatement.setString(2, refUser.getPassword());
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {	// when there is output
				String name = rs.getString("name");		// get name from database
				refUser.setName(name);					// then set it 
				refUser.setEmail(rs.getString("email"));	// directly set email with the query
				System.out.println("Welcome "+refUser.getName());	// print welcome to the user that log in
				boolean firstlogin = rs.getBoolean("firstlogin"); 	
				boolean status = rs.getBoolean("status");			
				String role = rs.getString("role");					
					if (role.equalsIgnoreCase("ADMIN")) {			// check role if it is admin 
						refLogin.adminPage();						// if role is admin will be pass over to adminPage method
					}
					else if (firstlogin == true) {					// role is not admin so the person is user and check if firstlogin is true
						String password = rs.getString("password");	// firstlogin is true then pass will be store inside password
						refUser.setPassword(password);				// and password will be set into user
						refLogin.firstLoginPage(refUser);			// user will be direct to firstLoginPage with user reference
					}
					else if (status==true) {						// user is not firstlogin then check if the user is lock or not
						
						userLogin(refUser);
					}	
			}else if((refUser.getNoofattempts()+1)<3) {				// when wrong password attempts will increase by 1 and by 3 the account will be lock
					try {
						String query2 = "UPDATE USER set noofattempts = ? where email =? ";
						PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
						refUser.setNoofattempts(refUser.getNoofattempts()+1);
						preparedStatement2.setInt(1, (refUser.getNoofattempts()));
						preparedStatement2.setString(2, refUser.getEmail());
						int rowcount = preparedStatement2.executeUpdate();
						if (rowcount>0) {
							System.out.println(refUser.getNoofattempts()+" attempts made, please enter the correct password.");
							refLogin.loginPage2(refUser);
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
			}
			else {		// upon 3 attempts, user will be lock by changing status to false
				try {
					String query3 = "UPDATE USER set status = ? where email =? ";
					PreparedStatement preparedStatement3 = conn.prepareStatement(query3);
					preparedStatement3.setBoolean(1, false);
					preparedStatement3.setString(2, refUser.getEmail());
					int rowcount = preparedStatement3.executeUpdate();
					if (rowcount > 0) {
						System.out.println("Your account have been lock. Please inform admin.");
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void userLogin(User refUser) {
		try {	// this will update attempts to 0 upon successfully log in
			String query4 = "UPDATE USER set noofattempts = ? where email =? ";
			PreparedStatement preparedStatement4 = conn.prepareStatement(query4);
			preparedStatement4.setInt(1, 0);
			preparedStatement4.setString(2, refUser.getEmail());
			int rowcount = preparedStatement4.executeUpdate();
			if (rowcount > 0) {
				refLogin.toContinue();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void addUser(User refUser) {
		try {	// this will create a new user with all the following detail being enter
			String query = "INSERT into USER(name, nric, email, dob, mobile, password, role, firstLogin, status, noofattempts) values (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getName());
			preparedStatement.setString(2, refUser.getNric());
			preparedStatement.setString(3, refUser.getEmail());
			preparedStatement.setString(4, refUser.getDob());
			preparedStatement.setString(5, refUser.getMobile());
			preparedStatement.setString(6, refUser.getPassword());
			preparedStatement.setString(7, "user");
			preparedStatement.setBoolean(8, true);
			preparedStatement.setBoolean(9, true);
			preparedStatement.setInt(10, 0);
			preparedStatement.executeUpdate();
			EmailPassword refEmail = new EmailPassword();
			refEmail.sendTempPassword(refUser);		// temp password will be pass over to email class sendTempPassword method to send as a email to user according to the email given
			preparedStatement.close();
			
		}	
		catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("User Created - Back to Home Page");
		
		refLogin.adminPage();		// after creating admin able to go back adminPage to do the choice
	}
	public void updateDetail(User refUser) {
		try {		// update password security question, answer and first login status with email 
			String query = "UPDATE USER set password = ?, securityQuestion =?, securityAnswer =?, firstLogin =? where email =?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getPassword());
			preparedStatement.setString(2, refUser.getSecurityQuestion());
			preparedStatement.setString(3, refUser.getSecurityAnswer());
			preparedStatement.setBoolean(4, false);
			preparedStatement.setString(5, refUser.getEmail());
			int rowcount = preparedStatement.executeUpdate();
			if (rowcount > 0) {
				refLogin.toContinue();	// endless loop for user  after updating
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void checkUser2(User refUser) {
		try {		// user forget password will enter email and it will get the security question, answer and status for the email enter
			String query ="SELECT securityQuestion, securityAnswer, status FROM USER WHERE email =?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getEmail());
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {	// if there is result which mean email is valid
				
				refUser.setSecurityQuestion(rs.getString("securityQuestion"));
				refUser.setSecurityAnswer(rs.getString("securityAnswer"));
				boolean status = rs.getBoolean("status");
				if (status) { // to check status of user
					refLogin.forgetPassword2(refUser);
				}else {
					System.out.println("Your account is locked. Please inform admin.");
				}
				
			}
			else {
				System.out.println("Invalid Login ID");
				refLogin.loginPage();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void viewUser() {
		try {		// admin to view all other user as admin is serial number 1 
			String query = "SELECT * FROM USER where serialNo != 1";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {		// it will print out all user available in database
				System.out.println(rs.getString("serialNo")+"\t"+rs.getString("name")+"\t\t"+rs.getString("nric")+"\t"+rs.getBoolean("status"));
			}
			System.out.println("1. To lock user using Serial No : ");
			System.out.println("2. To unlock user using Serial No : ");
			System.out.println("3. To return to main menu.");
			Scanner sc = new Scanner(System.in);
			LoginController refLoginController = new LoginController();
			refLoginController.adminChoice(sc.nextInt());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void lockUser(int choice) {
		try {		// changing the status of user with serial number
			String query = "UPDATE USER set status = ? where serialNo =?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			preparedStatement.setInt(2, choice);
			int rowcount = preparedStatement.executeUpdate();
			if (rowcount > 0) {
				refLogin.adminPage();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void unlockUser(int choice) {
		try {		// changing the status of user with serial number
			String query = "UPDATE USER set status = ?, noofattempts = ? where serialNo =?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setInt(2, 0);
			preparedStatement.setInt(3, choice);
			int rowcount = preparedStatement.executeUpdate();
			if (rowcount > 0) {
				refLogin.adminPage();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
} // end of loginDAO
