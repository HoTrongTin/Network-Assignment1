package basic;

public class UserAccount {
	private String userName;
	private String passWord;
	public UserAccount(String username,String password)
	{
		this.userName = username;
		this.passWord = password;
	}
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
	
}
