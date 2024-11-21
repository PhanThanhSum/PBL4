package model.BO;

public class Account {
	private String username;
	private String password;
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Account [username=" + username + ", password=" + password + "]";
	}
}
