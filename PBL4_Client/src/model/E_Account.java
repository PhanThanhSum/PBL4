package model;

public class E_Account {
	private String userName;
	private String passWord;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public E_Account(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}
	
}
