package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Util.XMLParseTool;
import Util.XMLGen;
import basic.Friend;
import basic.Protocol;

import server.ServerThread;



public class Server implements Runnable{
	public static final int REFRESH_TIME = 300000;
	private FriendList friendList;
	private JTextArea txtCommand;
	private ServerFrame frame;
	public Server(ServerFrame frame)
	{
		this.frame = frame;
		this.txtCommand = frame.txtCommand;
		friendList = new FriendList();	
	}
	@Override
	public void run() {
		
		//Thread  dung de kiem tra refesh list ban be tren server
		ServerCheckAccountThread checkThread = new ServerCheckAccountThread(friendList);
		checkThread.start();
		 
		 Socket s=null;
		 ServerSocket ss2=null;
		 
		 System.out.println("Server Listening......");
		 txtCommand.append( "\nServer Listening......");
		    try{
		        ss2 = new ServerSocket(frame.port); 
		       
		    }
		    catch(IOException e){
		    e.printStackTrace();
		    System.out.println("\n" +  "Server error");
		    txtCommand.setText(txtCommand.getText() + "\n" +  "Server error");
		    
		    }
		    
		    // running infinite loop for getting client login 
		    while(true){
		        try{
	                // socket object to receive incoming client requests 
		            s= ss2.accept();
		            System.out.println(  "\n " +  "connection Established + to " +s.getInetAddress().toString() + " port : " +  String.valueOf(s.getPort()));
		            txtCommand.setText("\n " +  "connection Established + to " +s.getInetAddress().toString() + " port : " +  String.valueOf(s.getPort()));
		           
		            ServerThread st=new ServerThread(s,frame,friendList);
		            st.start();

		        }

		        catch(Exception e){
		        	e.printStackTrace();
		        	System.out.println("\n " +  "Connection Error");
		        	txtCommand.setText(txtCommand.getText() + "\n " +  "Connection Error");

		        }
		    }

	}
		
	}
	
class ServerThread extends Thread{  
	ServerFrame frame;
	FriendList friendList;
    String line=null;
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;
    public ServerThread(Socket s,ServerFrame frame,FriendList friendList){
    	this.frame = frame;
        this.s=s;
        this.friendList = friendList;
       this.setName( s.getInetAddress() +"  - port : "  + String.valueOf( s.getPort())  + "   -   " + this.getName() + "  > ");
    }

    public void run() {
    
    try{
        is= new BufferedReader(new InputStreamReader(s.getInputStream()));
        os=new PrintWriter(s.getOutputStream());

    }catch(IOException e){
    	frame.txtCommand.append(" \n " + this.getName()+   "IO error in server thread");
        System.out.println( this.getName() +  "IO error in server thread");
    }

    try {
    	
        line=is.readLine();
        while(line.compareTo(XMLGen.genCHAT_CLOSE())!=0){
        	
        		
            if (XMLParseTool.isXMLCode(line))
            {
            	Document doc = XMLParseTool.genXMLDocument(line);
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
        						if(tagName.contains(Protocol.SESSION_LOG_IN))
        						{
        							System.out.println("\n " + this.getName() + " : " + "Session Log In");
        							frame.txtCommand.append("\n " + this.getName() + " : " + "Session Log In");
        							String username  = e.getElementsByTagName(Protocol.USERNAME).item(0).getTextContent();
        							String password = e.getElementsByTagName(Protocol.PASSWORD).item(0).getTextContent();
        							int port = Integer.parseInt(e.getElementsByTagName(Protocol.PORT).item(0).getTextContent());
        							Database database = new Database(frame.filePath);
        							if (database.checkLogin(username, password) == true)
        							{
        								Friend friend = new Friend(username,s.getInetAddress().getHostAddress(),port);
        								
        								friendList.addFriend(friend);
        								System.out.println("\n " + this.getName() + " : " + "Added new friend " + 
                								username + " " + s.getInetAddress().toString() + String.valueOf(port));
        								frame.txtCommand.append("\n " + this.getName() + " : " + "Added new friend " + 
        								username + " " + s.getInetAddress().toString() + String.valueOf(port));
        								System.out.println("\n " + this.getName() + " : "  + " Friend list have " + String.valueOf(friendList.getFriendCount()));
        								frame.txtCommand.append("\n " + this.getName() + " : "  + " Friend list have " + String.valueOf(friendList.getFriendCount()));
        								os.println(XMLGen.genBEGIN(0));
        								os.flush();
        							}
        							else
        							{
        								frame.txtCommand.append("\n " + this.getName() + " : " + "\n Log in fail");
        								System.out.println("\n " + this.getName() + " : " + "\n Log in fail");
        								os.println(XMLGen.genBEGIN(1));
        								os.flush();
        							}
        						}
        						else if (tagName.contains(Protocol.SESSION_REG))
        						{
        							System.out.println("\n " + this.getName() + " : " +  "session reg");
        							frame.txtCommand.append("\n " + this.getName() + " : " +  "session reg");
        							String username  = e.getElementsByTagName(Protocol.USERNAME).item(0).getTextContent();
        							String password = e.getElementsByTagName(Protocol.PASSWORD).item(0).getTextContent();
        							
        							Database db = new Database(frame.filePath);
        							if (db.userExists(username)==false)
        							{
        								db.addUser(username,password);
        								os.println(XMLGen.genREG_RESULT(0));
        								os.flush();
        								System.out.println("\n " + this.getName() + " : " + "tai khoan chua ton tai, them tai khoan moi thanh cong" );
        								frame.txtCommand.append("\n " + this.getName() + " : " + "tai khoan chua ton tai, them tai khoan moi thanh cong");
        							}
        							else
        							{	
        								System.out.println("\n " + this.getName() + " : " + "dang ki tai khoan that bai" );
        								frame.txtCommand.append("\n " + this.getName() + " : " + "dang ki tai khoan that bai");
        								os.println(XMLGen.genREG_RESULT(1));
        								os.flush();
        							}
        							
        						}
        						
        						else if (tagName.contains(Protocol.SESSION_GET_FRIEND_LIST_TEXT))
        						{
        							
        							System.out.println("\n " + this.getName() + " : get friend list");
        							frame.txtCommand.append("\n " + this.getName() + " : get friend list");
        							
        							os.println(XMLGen.genFRIEND_LIST(friendList.getFriendList()));
        							os.flush();
        						}
        						
        						else if (tagName.contains(Protocol.SESSION_KEEP_ALIVE))
        						{
        							
        							String username = e.getElementsByTagName(Protocol.USERNAME).item(0).getTextContent();
        							int status = Integer.parseInt(e.getElementsByTagName(Protocol.STATUS).item(0).getTextContent());
        							if (status == 0)
        							{
        								
        								friendList.keepAlive(username, 0);
        								System.out.println("\n " + this.getName() + " : " + username + "  update online");
        								frame.txtCommand.append("\n " + this.getName() + " : " + username + "  update online");
        							}
        							else
        							{
        								friendList.keepAlive(username, 1);
        								System.out.println("\n " + this.getName() + " : " + username + "  update offline");
        								frame.txtCommand.append("\n " + this.getName() + " : " + username + "  update offline");
        							}
        						}
        						
        					
        					}
        					
        					
        				}
        				
        			}
        			
        		} 
        		catch (Exception e)
        		{
        			
        		}
            	 
            }
          
            frame.txtCommand.append(" \n " + this.getName() + "command : \n"+line);
            line=is.readLine();
           
        } 
    } catch (IOException e) {

        line=this.getName(); //reused String line for getting thread name
        frame.txtCommand.append(" \n IO Error/ Client "+line+" terminated abruptly");
        System.out.println("IO Error/ Client "+line+" terminated abruptly");
    }
    catch(NullPointerException e){
        line=this.getName(); //reused String line for getting thread name
        frame.txtCommand.append(" \n Client "+ line +" Closed");
        System.out.println("Client "+line+" Closed");
    }

    finally{    
    try{
    	frame.txtCommand.append(" \n" + this.getName() + " Connection Closing..");
        System.out.println(" \n" + this.getName() + "Connection Closing..");
        if (is!=null){
            is.close(); 
            frame.txtCommand.append(" \n" + this.getName() + " Socket Input Stream Closed");
            System.out.println(" \n" + this.getName() + " Socket Input Stream Closed");
        }

        if(os!=null){
            os.close();
            frame.txtCommand.append(" \n" + this.getName() + " Socket Out Closed");
            System.out.println(" \n" + this.getName() + "Socket Out Closed");
        }
        if (s!=null){
        s.close();
        frame.txtCommand.append(" \n" + this.getName() + "  Socket Closed");
        System.out.println(" \n" + this.getName() + "Socket Closed");
        }

        }
    catch(IOException ie){
    	frame.txtCommand.append(" \n" + this.getName() + " Socket Close Error");
        System.out.println("Socket Close Error");
    }
    }
    }
}
class ServerCheckAccountThread extends Thread{
	private FriendList friendList = null;
	public ServerCheckAccountThread(FriendList friendList)
	{
		this.friendList = friendList;
	}
	public void run()
	{
		while(true)
		{
			if (friendList == null)
				return;
			try {
				this.sleep(Server.REFRESH_TIME);
				System.out.println("Server refesh list Total friend online have " +  String.valueOf(friendList.getFriendCount()));
				friendList.refeshList();
				friendList.resetStatus();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
	}
}