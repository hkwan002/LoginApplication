package com.optimum.pojo;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.optimum.controller.LoginController;
import com.optimum.dao.LoginDAO;

public class Login {

	static LoginController refLoginController = new LoginController();	//making reference to loginController class for the whole login class to use
	static Scanner sc = new Scanner(System.in);		
	
	static LoginDAO refDAO = new LoginDAO();		// making reference to loginDAO class
	static User refUser = new User();				// making reference to user class
	static LoginLogic refLoginLogic = new LoginLogic();	// making reference to loginLogic class
	
	public void frontPage() {	
		System.out.println("1. Login ");
		System.out.println("2. Forget Password ");
		System.out.print("Choose an Option : " );
		refLoginController.chooseOption(sc.nextInt());	// the next integer enter will pass to chooseOption method in loginController class
	}
	
	public void loginPage() {
		System.out.print("Enter Your Login ID : ");
		String inputEmail = sc.next();
		refUser.setEmail(inputEmail);	// user will input their email and save into user class email using set method
		refDAO.checkUser(refUser);		// calling loginDAO class checkUser method with user reference pass over
	}
	public void loginPage2(User refUser) {
		System.out.print("Enter Your Password : ");
		String inputPassword = sc.next();
		
		refUser.setPassword(inputPassword);		// user will input their password and save into user class password using set method
		refDAO.loginAuthentication(refUser);	// calling loginAuthentication method with user reference pass over
	}
	
	public void adminPage() {
		System.out.println("1. Register New User");
		System.out.println("2. View UserList");
		System.out.println("3. Logout");
		refLoginController.chooseAdminOption(sc.nextInt());	// the next integer enter will pass to chooseAdminOption method
	}
	
	public void registerPage() {
		System.out.print("Enter Name : ");
		refLoginLogic.nameCheck(refUser);	// name enter will be check by nameCheck method and return the value if true
		System.out.print("Enter NRIC : ");
		refLoginLogic.nricCheck(refUser);	// nric enter will be check by nricCHeck method and return the value if true
		System.out.print("Enter DOB (DD/MM/YYYY): ");
		refLoginLogic.dobCheck(refUser);	// dob enter will be check by dobCheck method and return the value if true
		System.out.print("Enter Email : ");
		refLoginLogic.emailCheck(refUser);	// email enter will be check by emailCheck method and return the value if true
		System.out.print("Enter Mobile : ");
		refLoginLogic.mobileCheck(refUser);	// mobile enter will be check by mobileCheck method and return the value if true
		refLoginLogic.generateTempPassword(refUser);	// temp password will be generated and return the value 
														// by right this temp password will not be display instead generated and send as email
		System.out.println();
		System.out.println("REGISTRATION DATA");					// show all the input by user to check if all correct
		System.out.println("Name : " + refUser.getName());
		System.out.println("NRIC : " + refUser.getNric());
		System.out.println("Date of Birth : " + refUser.getDob());
		System.out.println("Email : " + refUser.getEmail());
		System.out.println("Mobile : " + refUser.getMobile());
		
		System.out.println("Is the above information correct? (Y/N) : ");
		refLoginController.yesNoOption(sc.next(), refUser);		// user to input y or n and will pass to yesNoOption method with user reference pass over
		}
	
	public void firstLoginPage(User refUser) {
		System.out.print("Enter Old Password : ");
		refLoginLogic.checkTempPassword(refUser);		//password being enter will check with password retrieve during login that is set into password
	}
	
	public void securityQuestionPage(User refUser) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Security Question : ");
		System.out.println("A. What is your Favourite Animal?");
		System.out.println("B. What is your Favourite Superhero?");
		System.out.println("C. What is your Favourite Colour?");
		Pattern pattern;
		Matcher matcher;
		boolean questionCheck = false;
		System.out.println("Select your security question : (a,b or c)");
		while (!questionCheck) {		// while loop will run till questionCheck is true
			String question = sc.next();
			if (question.matches("[abcABC]")) {			//only 1character a b or c can be selected 
				refUser.setSecurityQuestion(question);	//set security question as input
				questionCheck = true;					//end while loop
			}else
				System.out.println("Invalid input. Please select your security question");	// error message if input is not match restart loop
			}
		System.out.print("Enter your answer : ");
		String securityAnswer = sc.next();
		refUser.setSecurityAnswer(securityAnswer);		// set security answer as input
		refDAO.updateDetail(refUser);					// call updateDetail method in LoginDAO with user reference pass over
	}

	public void toContinue() {
		
		System.out.println("Do you wish to continue?(Y/N) : ");
		refLoginController.toContinue(sc.next());	//endless loop upon user logging in
	}
	public void forgetPassword(User refUse) {
		
		System.out.print("Enter Your Login ID : ");
		String inputEmail = sc.next();
		refUser.setEmail(inputEmail);
		refDAO.checkUser2(refUser);
	}
	public void forgetPassword2(User refUse) {
		System.out.println("Security Question : ");
		System.out.println("A. What is your Favourite Animal?");
		System.out.println("B. What is your Favourite Superhero?");
		System.out.println("C. What is your Favourite Colour?");
		System.out.print("Select Your Security Question Choice : ");
		String question = sc.next();

		System.out.println("Enter Your Security Answer :");
		String answer = sc.next();
		if(refUser.getSecurityQuestion().equalsIgnoreCase(question) && refUser.getSecurityAnswer().equalsIgnoreCase(answer)) {
			LoginLogic refLoginLogic = new LoginLogic();		// to check input for security question and answer match with what they set during first login
			refLoginLogic.passwordComparison(refUser);			// if match will go to input new password
		} else {
			System.out.println("Please check your security question and answer.");
			forgetPassword2(refUser);		// re-enter security question and answer if enter wrongly
		}
	}	
} // end of login

