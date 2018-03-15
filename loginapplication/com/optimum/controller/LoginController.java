package com.optimum.controller;

import java.util.Scanner;

import com.optimum.dao.LoginDAO;
import com.optimum.pojo.Login;
import com.optimum.pojo.LoginLogic;
import com.optimum.pojo.User;

public class LoginController {

	static LoginDAO refLoginDAO = new LoginDAO();
	static Scanner sc = new Scanner(System.in);
	static User refUser = new User();
	static Login refLogin = new Login();

	public void chooseOption(int option) {
		switch (option) {
		case 1:
			System.out.println("Login Page");
			refLogin.loginPage();
			break;
		case 2:
			System.out.println("Forget Password");
			refLogin.forgetPassword(refUser);
			break;
		default:
			System.out.println("Please Select a Valid Option");
			refLogin.frontPage();
			break;
		}
	}

	public void chooseAdminOption(int option) {
		switch (option) {
		case 1:
			System.out.println("Register");
			refLogin.registerPage();
			break;
		case 2:
			System.out.println("View UserLists");
			refLoginDAO.viewUser();
			break;
		case 3:
			System.out.println("Thank you for using.");
			
			break;
		default:
			System.out.println("Please Select a Valid Option");
			refLogin.adminPage();
			break;
		}
	}

	public void yesNoOption(String option, User refUser) {
		if(option.equalsIgnoreCase("y")) {
			refLoginDAO.addUser(refUser);
			
		}
		else if(option.equalsIgnoreCase("n")) {
			System.out.println("Would you like to : ");
			System.out.println("1. Register New User");
			System.out.println("2. Go Back to Previous Page");
			System.out.println("Option : ");
			registrationOrPreviousPage(sc.nextInt());
		}
		else {
			System.out.println("Please Select a Valid Option (Y/N) : ");
		}
	}
	
	public void registrationOrPreviousPage(int option) {
		switch (option) {
		case 1:
			System.out.println("Register");
			refLogin.registerPage();
			break;
		case 2:
			refLogin.adminPage();
			break;
		default:
			break;
		}
	}
	public void toContinue(String option) {
		
		switch (option) {
		case "y":
		case "Y":
			refLogin.toContinue();
			break;
		case "n":
		case "N":
			System.out.println("Thank you for using");
			break;
		default:
			System.out.println("Invalid option");
			refLogin.toContinue();
			break;
		}
	}
	public void adminChoice(int option) {
		switch (option) {
		case 1:
			System.out.println("Enter serial No for user to be lock");
			refLoginDAO.lockUser(sc.nextInt());
			break;
		case 2:
			System.out.println("Enter serial No for user to be unlock");
			refLoginDAO.unlockUser(sc.nextInt());
			break;
		case 3:
			System.out.println("Would you like to : ");
			System.out.println("1. Register New User");
			System.out.println("2. Go Back to Previous Page");
			System.out.println("Option : ");
			registrationOrPreviousPage(sc.nextInt());
			break;

		default:
			System.out.println("Invalid input. Please enter your choice :");
			adminChoice(sc.nextInt());
			break;
		}
	}
} // end of LoginController

	
