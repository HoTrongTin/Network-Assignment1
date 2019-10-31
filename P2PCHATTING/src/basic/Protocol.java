package basic;

public interface Protocol {
	 String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	 
	 String PROTOCOL = "PROTOCOL";
	 String PROTOCOL_0 = "<PROTOCOL>";
	 String PROTOCOL_1 = "</PROTOCOL>";
	 
	 String SESSION_REG = "SESSION_REG";
	 String SESSION_REG_0 = "<SESSION_REG>";
	 String SESSION_REG_1 = "</SESSION_REG>";
	 
	 String USERNAME = "USERNAME";
	 String USERNAME_0 = "<USERNAME>";
	 String USERNAME_1 = "</USERNAME>";
	 
	 String PASSWORD = "PASSWORD";
	 String PASSWORD_0 = "<PASSWORD>";
	 String PASSWORD_1 = "</PASSWORD>";
	 
	 String SESSION_REG_RESULT = "SESSION_REG_RESULT";
	 String SESSION_REG_RESULT_0 = "<SESSION_REG_RESULT>";
	 String SESSION_REG_RESULT_1 = "</SESSION_REG_RESULT>";
	 
	 String SESSION_LOG_IN = "SESSION_LOG_IN";
	 String SESSION_LOG_IN_0 = "<SESSION_LOG_IN>";
	 String SESSION_LOG_IN_1 = "</SESSION_LOG_IN>";
	 
	 String PORT = "PORT";
	 String PORT_0 = "<PORT>";
	 String PORT_1 = "</PORT>";
	 
	 String SESSION_BEGIN = "SESSION_BEGIN";
	 String SESSION_BEGIN_0 = "<SESSION_BEGIN>";
	 String SESSION_BEGIN_1 = "</SESSION_BEGIN>";
	 
	 String RESULT = "RESULT";
	 String RESULT_0 = "<RESULT>";
	 String RESULT_1 = "</RESULT>";
	 
	 String SESSION_GET_FRIEND_LIST_TEXT = "SESSION_GET_FRIEND_LIST";
	 String SESSION_GET_FRIEND_LIST = "<SESSION_GET_FRIEND_LIST></SESSION_GET_FRIEND_LIST>";
	 
	 String SESSION_FRIEND_LIST = "SESSION_FRIEND_LIST";
	 String SESSION_FRIEND_LIST_0 = "<SESSION_FRIEND_LIST>";
	 String SESSION_FRIEND_LIST_1 = "</SESSION_FRIEND_LIST>";
	 
	 String FRIEND = "FRIEND";
	 String FRIEND_0 = "<FRIEND>";
	 String FRIEND_1 = "</FRIEND>";
	 
	 String ONLINE_STATUS = "ONLINE_STATUS";
	 String ONLINE_STATUS_0 = "<ONLINE_STATUS>";
	 String ONLINE_STATUS_1 = "</ONLINE_STATUS>";
	 
	 String IP = "IP";
	 String IP_0 = "<IP>";
	 String IP_1 = "</IP>";
	 
	 String SESSION_KEEP_ALIVE = "SESSION_KEEP_ALIVE";
	 String SESSION_KEEP_ALIVE_0 = "<SESSION_KEEP_ALIVE>";
	 String SESSION_KEEP_ALIVE_1 = "</SESSION_KEEP_ALIVE>";
	 
	 String STATUS = "STATUS";
	 String STATUS_0 = "<STATUS>";
	 String STATUS_1 = "</STATUS>";
	 
	 String CHAT_ACCEPT_TEXT = "CHAT_ACCEPT";
	 String CHAT_ACCEPT = "<CHAT_ACCEPT></CHAT_ACCEPT>";
	 
	 String CHAT_DENY_TEXT = "CHAT_DENY";
	 String CHAT_DENY = "<CHAT_DENY></CHAT_DENY>";
	 
	 String CHAT_MESS = "CHAT_MESS";
	 String CHAT_MESS_0 = "<CHAT_MESS>";
	 String CHAT_MESS_1 = "</CHAT_MESS>";
	 
	 String FILE_REQ = "FILE_REQ";
	 String FILE_REQ_0 = "<FILE_REQ>";
	 String FILE_REQ_1 = "</FILE_REQ>";
	 
	 String FILE_REQ_NOACK_TEXT = "FILE_REQ_NOACK";
	 String FILE_REQ_NOACK = "<FILE_REQ_NOACK></FILE_REQ_NOACK>";
	 
	 String FILE_REQ_ACK = "FILE_REQ_ACK";
	 String FILE_REQ_ACK_0 = "<FILE_REQ_ACK>";
	 String FILE_REQ_ACK_1 = "</FILE_REQ_ACK>";
	 
	 String FILE_DATA_BEGIN_TEXT  = "FILE_DATA_BEGIN";
	 String FILE_DATA_BEGIN  = "<FILE_DATA_BEGIN></FILE_DATA_BEGIN>";
	 
	 String FILE_DATA = "FILE_DATA";
	 String FILE_DATA_0 = "<FILE_DATA>";
	 String FILE_DATA_1 = "<FILE_DATA/>";
	 
	 String FILE_DATA_END_TEXT = "FILE_DATA_END";
	 String FILE_DATA_END = "<FILE_DATA_END></FILE_DATA_END>";
	 
	 String CHAT_CLOSE_TEXT = "CHAT_CLOSE";
	 String CHAT_CLOSE = "<CHAT_CLOSE></CHAT_CLOSE>";
}
