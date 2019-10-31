package basic;

public class Friend {
	private String accountName;
	private String IPAdress;
	private int port;
	
	public Friend(String accountName, String IPAdress,int port)
	{
		this.accountName = accountName;
		this.IPAdress = IPAdress;
		this.port = port;
		
	}
	@Override
	public String toString() {
		return accountName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountname) {
		this.accountName = accountname;
	}
	public String getIPAdress() {
		return IPAdress;
	}
	public void setIPAdress(String iPAdress) {
		IPAdress = iPAdress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
