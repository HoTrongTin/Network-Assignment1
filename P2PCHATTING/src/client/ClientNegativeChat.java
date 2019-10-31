package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Util.XMLGen;
import Util.XMLParseTool;
import basic.Protocol;

public class ClientNegativeChat implements Runnable{
	@SuppressWarnings("resource")
	public void run() {
		 Socket s=null;
		 ServerSocket ss2=null;
		 System.out.println("Client Host Listening......");
		    try{
		        ss2 = new ServerSocket(port);     
		        System.out.println("Client host is created with port: " + String.valueOf(port));
		    }
		    catch(IOException e){
		    	e.printStackTrace();
		    	System.out.println( "Client Host Server error");
		    	JOptionPane.showMessageDialog(null, "Can't not create this host");
		  	    return;
		    }

		    while(true){
		        try{
		           s= ss2.accept();
		           BufferedReader recieve = new BufferedReader(new InputStreamReader(s.getInputStream()));
		           String friendName = recieve.readLine();
		           HandleAChatRequest hostA = new HandleAChatRequest(s, friendName);
		           Thread thread = new Thread(hostA);
		           thread.start();
		        }
		        catch(Exception e){
		        	e.printStackTrace();
		        	System.out.println(  "\n" +  "Client Host connection Error ");

		    }
		    }

	}
	
	private int port;
	public ClientNegativeChat(int port)
	{
		
		
		this.port = port;
		
	}
}
class ClientNegativeRecieve implements Runnable
{
	Socket clientSocket=null;
	BufferedReader brBufferedReader = null;
	private ChatFrame chatBox= null;
	private String friendName;
	public ClientNegativeRecieve(Socket clientSocket,ChatFrame chatbox,String friendName)
	{
		this.clientSocket = clientSocket;
		this.chatBox = chatbox;
		this.friendName = friendName;
	}//end constructor
	public void run() {
		
		try{
			BufferedReader recieve = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			String msgRecieved = null;
			while((msgRecieved = recieve.readLine())!= null)
			{
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
	        						
	        						if (tagName.contains(Protocol.CHAT_CLOSE_TEXT))
	        						{
	        							chatBox.btnEndChat.setEnabled(false);
	        							chatBox.btnSendFile.setEnabled(false);
	        							chatBox.btnSendMess.setEnabled(false);
	        							chatBox.txtConversation.append("\nEnd conversation");
	        							clientSocket.close();
	        							break;
	        						}
	        						else if(tagName.contains(Protocol.CHAT_MESS))
	        						{
	        							String mess = e.getTextContent();
	        							chatBox.txtConversation.append("\n[" + friendName + "]: " + mess);
	        						}
	        						else if (tagName.contains(Protocol.FILE_REQ_ACK))
	        						{
	        							chatBox.txtConversation.append("\nYour friend want to receive file!");
	        							int port = Integer.parseInt(e.getTextContent());
	        							SendFile send = new SendFile(clientSocket.getInetAddress().getHostAddress(), port);
	        							new Thread(send).start();
	        						}
	        						else if (tagName.contains(Protocol.FILE_REQ_NOACK_TEXT))
	        						{
	        							chatBox.txtConversation.append("\nYour friend decline to receive file!");
	        						}
	        						else if (tagName.contains(Protocol.FILE_REQ))
	        						{
	        							chatBox.txtConversation.append("\nYour friend want to send you a file!");
	        							RecieveFileFrame farm = new RecieveFileFrame(clientSocket);
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
				
			}catch(Exception e){System.out.println(e.getMessage());}
		}

} 

class ClientNegativeSend implements Runnable
{
	PrintWriter pwPrintWriter;
	Socket clientSock = null;
	private String mess;
	private ChatFrame chatBox;
	public ClientNegativeSend(Socket clientSock ,String mess,ChatFrame chatBox)
	{
		this.clientSock = clientSock;
		this.mess = mess;
		this.chatBox = chatBox;
	}
	public void run() {
	try{
		if(clientSock.isClosed() ==false)
		{
			
			this.pwPrintWriter = new PrintWriter(clientSock.getOutputStream(), true);	
			this.pwPrintWriter.println(XMLGen.genChatMess(mess));
			this.pwPrintWriter.flush();
			chatBox.txtConversation.append("\n[Me]: " + mess);
		}
		else
		{
			chatBox.txtConversation.append("\n Error : Can't send this mess");
		}
		
		
		}
		catch(Exception ex){System.out.println(ex.getMessage());}	
	}//end run
}

class HandleAChatRequest implements Runnable{
	private Socket s;
	private String friendName;
	public HandleAChatRequest(Socket socket,String friendName)
	{
		this.s = socket;
		this.friendName = friendName;
	}
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		try{
	
         
			
		
         PrintWriter os = new PrintWriter(s.getOutputStream());
		
         System.out.println("Connection is established to "+ s.getInetAddress().toString() + " - port : " + String.valueOf(s.getPort()) );
         int option = JOptionPane.showConfirmDialog(null, "Do you want to chat with friend have " + friendName , "Friend chat request", JOptionPane.YES_NO_OPTION);
    
         if (option == 0 )
         {
         	
         	 ChatFrame chatBox = new ChatFrame(false, s,friendName);
     		 chatBox.show();
     		 chatBox.btnEndChat.setEnabled(true);
     		 chatBox.btnSendFile.setEnabled(true);
     		 chatBox.btnSendMess.setEnabled(true);
     		 SendCommand sendCommand = new SendCommand(s, XMLGen.genChatAccept());
     		new Thread(sendCommand).start();
        	 
     		 ClientNegativeRecieve reciever = new ClientNegativeRecieve(s, chatBox, friendName);
     		 Thread recieveThread = new Thread(reciever);
     		 recieveThread.run();
						
				
         }
         else
         {
         	os.println(XMLGen.genChatDeny());
         	os.flush();
         	System.out.println("Client host have decline a chat");
         	
         }
		}
		catch (Exception e){}
	
	}
	
	
	
}
