package com.optimum.application;

import com.optimum.pojo.Login;
import com.optimum.controller.LoginController;

public class LoginApplication {

	public static void main(String[] args) {
		
		Login refLogin = new Login();	// making reference to login class
		refLogin.frontPage();			// calling login class frontpage method
	}
} // end of loginApplication
