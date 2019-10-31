package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Util.XMLParseTool;
import Util.XMLGen;
import basic.Protocol;

public class ClientActiveChat implements Runnable{
	private String friendName;
	private String yourName;
	private String IPAddress;
	private int port;
	private ChatFrame chatbox;
	public ClientActiveChat(String friendName,String yourname,String IPAddress, int port)
	{
		this.yourName = yourname;
		this.friendName = friendName;
		this.IPAddress = IPAddress;
		this.port = port;
	}
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Socket sock;
		try {
			sock = new Socket(IPAddress,port);
			SendCommand send = new SendCommand(sock,yourName);
			new Thread(send).run();
			chatbox = new ChatFrame(true,sock,friendName);
			chatbox.show();
			chatbox.btnSendFile.setEnabled(false);
			chatbox.btnSendMess.setEnabled(false);
			chatbox.btnEndChat.setEnabled(false);
			chatbox.txtConversation.setText("Send a chat request to your friend");
			ClientActiveRecieve recieveThread = new ClientActiveRecieve(sock,friendName,chatbox);
			Thread threadRecieve =new Thread(recieveThread);
			//start() call the run() method of ClientActiveRecieve class
			threadRecieve.start();
			
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Can't connect to this friend");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Something happen, can't not create connection");
			e.printStackTrace();
		}
		
	}
	
}

class ClientActiveRecieve implements Runnable
{
	Socket sock=null;
	BufferedReader recieve=null;
	private String friendName;
	private ChatFrame chatBox;
	public ClientActiveRecieve(Socket sock,String friendName,ChatFrame chatBox) {
		this.chatBox = chatBox;
		this.friendName  = friendName;
		this.sock = sock;
	}
	public void run() {
		try{
		recieve = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
		String msgRecieved = null;
		while((msgRecieved = recieve.readLine())!= null)
		{
			System.out.println("Da nhan thong tin phan hoi + \n + " + msgRecieved);
			if (XMLParseTool.isXMLCode(msgRecieved))
            {
            	Document doc = XMLParseTool.genXMLDocument(msgRecieved);
            	try {			
        			NodeList ele = doc.getElementsByTagName(Protocol.PROTOCOL);
        			for (int i = 0; i < ele.getLength(); i++)
        			{
        				
        				NodeList child = ele.item(i).getChildNodes();
        				for(int j = 0; j < child.getLength();j++)
        				{
        					
        					Node node = child.item(j);					
        					if(node.getNodeType() ==Node.ELEMENT_NODE)
        					{
        						Element e = (Element) node;
        						String tagName = e.getTagName();
        						
        						//BEGIN PARSE
        						if(tagName.contains(Protocol.CHAT_ACCEPT_TEXT))
        						{
        							
        							chatBox.btnEndChat.setEnabled(true);
        							chatBox.btnSendFile.setEnabled(true);
        							chatBox.btnSendMess.setEnabled(true);
        							chatBox.txtConversation.append("\nYour friend have accepted your request! ");
        							break;
        						}
        						else if (tagName.contains(Protocol.CHAT_DENY_TEXT))
        						{
        							System.out.println("deny");
        							chatBox.txtConversation.append("\nYour friend not accept to chat");
        							sock.close();
        							break;
        							
        						}
        						else if (tagName.contains(Protocol.CHAT_CLOSE_TEXT))
        						{
        							chatBox.btnEndChat.setEnabled(false);
        							chatBox.btnSendFile.setEnabled(false);
        							chatBox.btnSendMess.setEnabled(false);
        							chatBox.txtConversation.append("\nEnd conversation");
        							sock.close();
        							break;
        						}
        						else if(tagName.contains(Protocol.CHAT_MESS))
        						{
        							
        							String mess = e.getTextContent();
        							chatBox.txtConversation.append("\n[" + friendName + "]: "  +  mess);
        						}
        					
        						else if (tagName.contains(Protocol.FILE_REQ_ACK))
        						{
        							chatBox.txtConversation.append("\nYour friend want to recieve file");	//Sau khi chon thu muc luu
        							int port = Integer.parseInt(e.getTextContent());
        							SendFile send = new SendFile(sock.getInetAddress().getHostAddress(), port);
        							new Thread(send).start();
        						}
        						else if (tagName.contains(Protocol.FILE_REQ_NOACK_TEXT))
        						{
        							chatBox.txtConversation.append("\nYour friend won't to recieve file");
        						}
        						else if (tagName.contains(Protocol.FILE_REQ))
        						{
        							chatBox.txtConversation.append("\nYour friend want to send you a file"); //Khi nhan lenh SendFile
        							RecieveFileFrame farm = new RecieveFileFrame(sock);
        							farm.show();
        						}
        					}
        				}
        			}
            	}
            	catch (Exception e)
        		{
        			
        		}
            }
		}
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}

class ClientActiveSend implements Runnable
{
	Socket sock=null;
	PrintWriter print=null;
	private String mess;
	private ChatFrame chatBox;
	public ClientActiveSend(Socket sock, String mess,ChatFrame chatBox)
	{
		this.mess = mess;
		this.sock = sock;
		this.chatBox = chatBox;
	}
	public void run(){
		try{
			if(sock.isClosed()==false)
			{
				this.print = new PrintWriter(sock.getOutputStream(), true);	
				this.print.println(XMLGen.genChatMess(mess));
				this.print.flush();
				chatBox.txtConversation.append("\n[Me]: " + mess);
			}
			else
			{
				chatBox.txtConversation.append("\nError: Can't send this mess");
			}
		
		}
	catch(Exception e){System.out.println(e.getMessage());}
	}
}

