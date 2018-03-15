package com.optimum.pojo;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.optimum.dao.LoginDAO;
public class LoginLogic {

	
	static Scanner sc = new Scanner(System.in);	
	static Pattern pattern;
	static Matcher matcher;
	static User refUser = new User();
	static LoginDAO refDAO = new LoginDAO();
	static Login refLogin = new Login();


	public void nameCheck(User refUser) {
		boolean nameCheck = false;
		while(!nameCheck) {
		
			String name = sc.nextLine();
			if(name.matches("([a-zA-Z]+\\s)*[a-zA-Z]*") && !name.equals("")) {		// ensure name enter is not null and only alphabet
				refUser.setName(name);
				nameCheck=true;
			}
			else {
				System.out.println("Invalid Input. Please Enter a Valid Name : ");
			}
		}
	}

	public void nricCheck(User refUser) {
		boolean nricCheck = false;
		while (!nricCheck) {
			String nric = sc.next();
			if (nric.matches("[a-zA-Z][0-9]{7}[a-zA-Z]")) {		// ensure nric enter start with alphabet, 7digits then end with alphabet
				refUser.setNric(nric);
				nricCheck=true;
			}
			else
				System.out.println("Invalid Input. Please Enter a Valid NRIC : ");
		}
		
	}

	public void dobCheck(User refUser) {
		boolean dobCheck = false;
		while(!dobCheck) {
			String dob=sc.next();
			// check dob enter that first 2 is dd then mm and follow by yyyy and in between will be /
			String dobRegex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

			pattern = Pattern.compile(dobRegex);
			matcher = pattern.matcher(dob);
			if(matcher.matches()) {
				matcher.reset();
				if(matcher.find()) {
				String dd=matcher.group(1);	
				String mm=matcher.group(2);	
				int yy = Integer.parseInt(matcher.group(3));	// months that cannot have 31days
					if(dd.equals("31") && (mm.equals("04") || mm.equals("06") || mm.equals("09") || mm.equals("11") )){
						dobCheck = false;
					}
					else if(mm.equals("02")) {	// month that cannot have 30 and 31days
						if(yy%4==0) {	
							if(dd.equals("30") || dd.equals("31")) {	
								dobCheck = false;
							}
							else {
								dobCheck = true;
							}
						}
						else {		// check for leap year
							if(dd.equals("29") || dd.equals("30") || dd.equals("31")){	
								dobCheck = false;
							}
							else {
								dobCheck = true;
							}
						}
					}
					else {
						dobCheck = true;
					}
				}
				else {
					dobCheck = false;
				}
			}
			else {
				dobCheck = false;
			}
			if(dobCheck==true) {	// changing back to date format to enable it to store inside database as date
				String inputDob = dob.substring(6,10)+"-"+dob.substring(3, 5)+"-"+dob.substring(0,2);
				refUser.setDob(inputDob);
			}
			else {
				System.out.print("Invalid Input. Please Enter a Date of Birth : ");
			}
		}
	}

	public void emailCheck(User refUser) {
		boolean emailCheck=false;
		while(!emailCheck) {		// check for word before @ without spacing with ending more than 2alphabet after .
		String email=sc.next();
		String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		
		pattern = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(email);
		emailCheck = matcher.matches();
		if(emailCheck==true) {
			refUser.setEmail(email);
//			emailCheck=true;
		}
		else {
			System.out.print("Invalid Input. Please Enter a Valid Email Address : ");
		}
		}
	}

	public void mobileCheck(User refUser) {
		boolean mobileCheck=false;
		while(!mobileCheck) {
			String mobile = sc.next();
				// check mobile enter is 8digits
			if(mobile.matches("[0-9]{8}")) {
				refUser.setMobile(mobile);
				mobileCheck = true;
			}
			else {
				System.out.print("Invalid Input. Please Enter a Valid Mobile Number: ");	
			}
		}
	}

	public void generateTempPassword(User refUser) {	//temp password generate by using first 4number of nric and last 4number mobile
		String nric = refUser.getNric();
		String mobile = refUser.getMobile();
		String tempPassword = nric.substring(1, 5)+mobile.substring(4, 8);
		System.out.println("Your one time password is : "+tempPassword);
		refUser.setPassword(tempPassword);
	}

	public void checkTempPassword(User refUser) {
		boolean tempPasswordCheck = false;		// to check password enter is same as password save during log in for first login
		String oldTempPassword = refUser.getPassword();
		while(!tempPasswordCheck) {
			String tempPassword=sc.next();
			if (tempPassword.equals(oldTempPassword)) {
				tempPasswordCheck = true;
				passwordComparison(refUser);
			}
			else {
				System.out.print("Please Re-Enter Old Password : ");
			}
		}
	}

	public void passwordComparison(User refUser) {	// to check new password enter and re-enter new password is same
		String passOne, passTwo;
		boolean compareCheck = false;
		while(!compareCheck) {
			System.out.print("Enter New Password : ");
			passOne = sc.next();
			while(passOne.equals(refUser.getPassword())) {
				System.out.println("New Password CANNOT be the same as the Previous Password");
				System.out.print("Enter New Password : ");
				passOne = sc.next();
			}
			System.out.print("Re-Enter New Password : ");
			
			passTwo = sc.next();
			if(passTwo.equals(passOne)) {
				compareCheck = true;
				refUser.setPassword(passOne);
				refLogin.securityQuestionPage(refUser);
			}
			else {
				System.out.println("Incorrect Password");
			}
		}
	}
} // end of LoginLogic

