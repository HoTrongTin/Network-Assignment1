package Util;

import java.util.ArrayList;


import basic.Friend;
import basic.Protocol;
import basic.UserAccount;
public class XMLGen {

	private static String genXML(String s)
	{
		return 
				Protocol.XMLHEAD + 
				Protocol.PROTOCOL_0 
				+ s
				+ Protocol.PROTOCOL_1;
	}
	public static String genREG(UserAccount account)
	{
		String s = new String("");

		s = s + Protocol.USERNAME_0 +   account.getUserName() + Protocol.USERNAME_1 
				+	Protocol.PASSWORD_0  + account.getPassWord()  + Protocol.PASSWORD_1 ;
		s = genXML(Protocol.SESSION_REG_0 
					+ s 
				+ Protocol.SESSION_REG_1 );
				
				
		return s;
	}

	/**
	 * 0 reg success
	*1 account already exist
	* can't connect to db
	 */
	public static String genREG_RESULT(int number)
	{
		
		return genXML(Protocol.SESSION_REG_RESULT_0  + String.valueOf(number) + Protocol.SESSION_REG_RESULT_1 );
	}
	public static String genLOG_IN(UserAccount account, int port)
	{
		String s = new String("");
		s = Protocol.SESSION_LOG_IN_0 
			+Protocol.USERNAME_0 + account.getUserName()  + Protocol.USERNAME_1 
			+ Protocol.PASSWORD_0  + account.getPassWord()  + Protocol.PASSWORD_1 
			+ Protocol.PORT_0 + String.valueOf(port) + Protocol.PORT_1 + 
			Protocol.SESSION_LOG_IN_1 ;
		return genXML(s);
	}
	/**
	 * 0 ket noi thanh cong
	 * 1 ket noi khong thanh cong
	 */
	public static String genBEGIN(int number)
	{
		
		String s = new String("");
		s = Protocol.SESSION_BEGIN_0 
				+ String.valueOf(number)
			+Protocol.SESSION_BEGIN_1 ;
		return genXML(s);
	}
	public static String genGET_FRIEND_LIST()
	{
		return genXML(Protocol.SESSION_GET_FRIEND_LIST );
	}
	public static String genFRIEND_LIST(ArrayList<Friend> list)
	{
		String s = new String("");
		if (list != null)
		{
			for (int i = 0 ; i < list.size();i++)
			{
				s = s + Protocol.FRIEND_0+
						Protocol.USERNAME_0 + list.get(i).getAccountName() + Protocol.USERNAME_1 + 
						Protocol.ONLINE_STATUS_0 + String.valueOf(0) + Protocol.ONLINE_STATUS_1 +  
						Protocol.IP_0  + list.get(i).getIPAdress()  + Protocol.IP_1 + 
						Protocol.PORT_0 + list.get(i).getPort() + Protocol.PORT_1 + 
					Protocol.FRIEND_1 
				;
			}
		}
		
		return genXML(Protocol.SESSION_FRIEND_LIST_0 
				+ s+ 
				Protocol.SESSION_FRIEND_LIST_1
				);
	}
	public static String genKEEP_ALIVE(String userName,int isOnline)
	{
		String s = new String("");
		s = Protocol.SESSION_KEEP_ALIVE_0
				 + Protocol.USERNAME_0  + userName + Protocol.USERNAME_1 
				 + Protocol.STATUS_0 + String.valueOf(isOnline) + Protocol.STATUS_1 
				+ 
			Protocol.SESSION_KEEP_ALIVE_1 ;
		return genXML(s);
	}
	public static String genChatDeny()
	{
		
		return genXML(Protocol.CHAT_DENY );
	}
	public static String genChatAccept()
	{
		return genXML(Protocol.CHAT_ACCEPT  );
	}
	public static String genChatMess(String s)
	{
		return genXML(Protocol.CHAT_MESS_0 + s   + Protocol.CHAT_MESS_1);
	}
	public static String genFILE_REQ(String filename)
	{
		return genXML(Protocol.FILE_REQ_0 + filename + Protocol.FILE_REQ_1 );
	}
	public static String genFILE_REQ_NOACK()
	{
		return genXML(Protocol.FILE_REQ_NOACK );
	}
	public static String genFILE_REQ_ACK(int port)
	{
		String s = 
		Protocol.FILE_REQ_ACK_0 
				 + String.valueOf(port)
				+ Protocol.FILE_REQ_ACK_1 	;
		return genXML(s);
	}
	public static String genCHAT_CLOSE()
	{
		
		return genXML( Protocol.CHAT_CLOSE );
	}
	
	
}
