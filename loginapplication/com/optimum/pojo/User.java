package com.optimum.pojo;

public class User {
	private String name, email, password, nric, dob, role;
	private String mobile;
	private String securityQuestion;
	private String securityAnswer;
	private boolean firstlogin;
	private boolean status;
	private int noofattempts;
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setNric(String nric) {
		this.nric = nric;
	}
	public String getNric() {
		return nric;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMobile() {
		return mobile;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRole() {
		return role;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setFirstlogin(boolean firstlogin) {
		this.firstlogin = firstlogin;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public void setNoofattempts(int noofattempts) {
		this.noofattempts = noofattempts;
	}
	public int getNoofattempts() {
		return noofattempts;
	}
	public boolean isFirstlogin() {
		return firstlogin;
	}
	public boolean isStatus() {
		return status;
	}
} // end of user
