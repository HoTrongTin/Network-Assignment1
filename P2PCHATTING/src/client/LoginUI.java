package client;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;

import javax.swing.JPasswordField;

import Util.XMLParseTool;
import Util.XMLGen;
import basic.Friend;
import basic.Protocol;
import basic.UserAccount;
import server.ServerFrame;

import java.util.*;

import javax.swing.JOptionPane;

import org.w3c.dom.*;
public class LoginUI {

	private JFrame frame;
	private JTextField txtName;
	private JTextField txtPort;
	private JPasswordField txtPass;
	private String userName;
	private String passWord;
	private int userConnectionPort;
	private String svIP;
	private int port;
	private Boolean isConnected = false;
	private Socket sock;
    private BufferedReader reader;
    private ArrayList<Friend> frList = new ArrayList<Friend>();
    PrintWriter writer;
    private JTextField txtsvIP;
    private FListUI f;
	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public LoginUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel lblUsername = new JLabel("Username");
		springLayout.putConstraint(SpringLayout.NORTH, lblUsername, 41, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblUsername, 28, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 28, SpringLayout.SOUTH, lblUsername);
		springLayout.putConstraint(SpringLayout.EAST, lblPassword, 0, SpringLayout.EAST, lblUsername);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblPort = new JLabel("Server Port");
		springLayout.putConstraint(SpringLayout.EAST, lblPort, -85, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(lblPort);
		
		txtName = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtName, -3, SpringLayout.NORTH, lblUsername);
		springLayout.putConstraint(SpringLayout.WEST, txtName, 61, SpringLayout.EAST, lblUsername);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		txtPass = new JPasswordField();
		springLayout.putConstraint(SpringLayout.NORTH, txtPass, 19, SpringLayout.SOUTH, txtName);
		springLayout.putConstraint(SpringLayout.WEST, txtPass, 0, SpringLayout.WEST, txtName);
		springLayout.putConstraint(SpringLayout.EAST, txtPass, 0, SpringLayout.EAST, txtName);
		frame.getContentPane().add(txtPass);
		
		txtPort = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, lblPort, 0, SpringLayout.WEST, txtPort);
		springLayout.putConstraint(SpringLayout.SOUTH, lblPort, -6, SpringLayout.NORTH, txtPort);
		springLayout.putConstraint(SpringLayout.EAST, txtPort, -50, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(txtPort);
		txtPort.setColumns(10);
		
		JButton btReg = new JButton("Sign up");
		btReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SignUp signupFrame = new SignUp();
				signupFrame.setDefaultCloseOperation(1);
				signupFrame.show();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btReg, 183, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btReg, -41, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btReg, 29, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btReg, -300, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btReg);
		
		JButton btLogIn = new JButton("Sign in");
		
		springLayout.putConstraint(SpringLayout.NORTH, btLogIn, 0, SpringLayout.NORTH, btReg);
		springLayout.putConstraint(SpringLayout.WEST, btLogIn, 22, SpringLayout.EAST, btReg);
		springLayout.putConstraint(SpringLayout.SOUTH, btLogIn, 0, SpringLayout.SOUTH, btReg);
		springLayout.putConstraint(SpringLayout.EAST, btLogIn, -173, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btLogIn);
		
		JButton btExit = new JButton("Exit");
		springLayout.putConstraint(SpringLayout.NORTH, btExit, 0, SpringLayout.NORTH, btReg);
		springLayout.putConstraint(SpringLayout.WEST, btExit, 19, SpringLayout.EAST, btLogIn);
		springLayout.putConstraint(SpringLayout.SOUTH, btExit, 0, SpringLayout.SOUTH, btReg);
		springLayout.putConstraint(SpringLayout.EAST, btExit, -49, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btExit);
		
		JLabel lblServerIp = new JLabel("Server IP");
		springLayout.putConstraint(SpringLayout.NORTH, lblServerIp, 0, SpringLayout.NORTH, lblUsername);
		springLayout.putConstraint(SpringLayout.WEST, lblServerIp, 0, SpringLayout.WEST, lblPort);
		frame.getContentPane().add(lblServerIp);
		
		txtsvIP = new JTextField();
		txtsvIP.setText("localhost");
		springLayout.putConstraint(SpringLayout.NORTH, txtsvIP, 6, SpringLayout.SOUTH, lblServerIp);
		springLayout.putConstraint(SpringLayout.NORTH, lblPort, 16, SpringLayout.SOUTH, txtsvIP);
		springLayout.putConstraint(SpringLayout.WEST, txtsvIP, 0, SpringLayout.WEST, lblPort);
		frame.getContentPane().add(txtsvIP);
		txtsvIP.setColumns(10);
		
		JLabel label = new JLabel("Port");
		springLayout.putConstraint(SpringLayout.NORTH, txtPort, -3, SpringLayout.NORTH, label);
		springLayout.putConstraint(SpringLayout.NORTH, label, 23, SpringLayout.SOUTH, lblPassword);
		springLayout.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, lblUsername);
		springLayout.putConstraint(SpringLayout.EAST, label, 0, SpringLayout.EAST, lblUsername);
		frame.getContentPane().add(label);
		
		JTextField txtConnectionPort = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, txtConnectionPort, 0, SpringLayout.SOUTH, label);
		springLayout.putConstraint(SpringLayout.EAST, txtConnectionPort, 0, SpringLayout.EAST, txtName);
		txtConnectionPort.setColumns(10);
		frame.getContentPane().add(txtConnectionPort);
		
		btLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isConnected == true){
					JOptionPane.showMessageDialog(null, "You are connected");
				}
				else {
				userName = txtName.getText();
				passWord = txtPass.getText();
				svIP = txtsvIP.getText();
				userConnectionPort = Integer.parseInt(txtConnectionPort.getText());
				System.out.println(passWord);
				port = Integer.parseInt(txtPort.getText());
				UserAccount usr = new UserAccount(userName,passWord);
				String s = XMLGen.genLOG_IN(usr, userConnectionPort);
					try {
						sock = new Socket(svIP,port);
						UpdateStatusThread update = new UpdateStatusThread(sock, userName);
						new Thread(update).start();
						PrintWriter writer = new PrintWriter(new DataOutputStream(sock.getOutputStream()));
			             writer.println(s);
			             writer.flush(); 	
			             BufferedReader buffer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			 			String re = buffer.readLine();
			 			int result = 1;
			 			String line = re;
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
			        					//IMPLEMENT PHAN NAY
			        					Node node = child.item(j);					
			        					if(node.getNodeType() == Node.ELEMENT_NODE)
			        					{
			        						Element ee = (Element) node;
			        						String tagName = ee.getTagName();
			        						if (tagName.contains(Protocol.SESSION_REG_RESULT))
									{
										
										 result =  Integer.parseInt(ee.getTextContent());
										
									}
				
									else if (tagName.contains(Protocol.SESSION_BEGIN))
									{
										
										 result =  Integer.parseInt(ee.getTextContent());
									
									}
			        					}}}}
			            	catch(Exception ab){
			            		ab.printStackTrace();}}
			           	
					
					if(result ==0){
						
						PrintWriter writer1 = new PrintWriter(new DataOutputStream(sock.getOutputStream()));
			             writer1.println(XMLGen.genGET_FRIEND_LIST());
			             writer1.flush(); 	
			             BufferedReader buffer1 = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			 			String re1 = buffer1.readLine();
				
					String linee = re1;
			    		System.out.println(linee);
						if (XMLParseTool.isXMLCode(linee))
						{
							Document doc = XMLParseTool.genXMLDocument(linee);
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
											Element ex = (Element) node;
											String tagName = ex.getTagName();
											
											if (tagName.contains(Protocol.SESSION_FRIEND_LIST))
											{
												System.out.println("session freind list");
												ArrayList<Friend> list = new ArrayList<Friend>();
												NodeList nList = doc.getElementsByTagName(Protocol.FRIEND);
												for (int k = 0; k < nList.getLength(); k++)
												{
													Node nNode = nList.item(k);
													if (nNode.getNodeType() == Node.ELEMENT_NODE)
													{
														
														Element eElement = (Element) nNode;
														String username = eElement.getElementsByTagName(Protocol.USERNAME).item(0).getTextContent();
														String ip = eElement.getElementsByTagName(Protocol.IP).item(0).getTextContent();
														int port = Integer.parseInt(eElement.getElementsByTagName(Protocol.PORT).item(0).getTextContent());
														Friend newFriend = new Friend(username,ip,port);
														list.add(newFriend);
														
														
													}
													
												}
												System.out.print(String.valueOf(list.size())+ " size");
												frList = list;
											
												
											}
											
														
										
										}
										
										
									}
									
								}
							}	
							catch (Exception ec)
							{
								
							}
						}
			    	
			    	System.out.println("FriendList hien co : "+ frList.size());	
					f = new FListUI(sock,frList,userName,userConnectionPort,svIP,port);// tao ra giao dien cua friendList
					f.setVisible(true);
					
					
					frame.setDefaultCloseOperation(1);
					frame.setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null, "Login Error");
					}
					}
					 catch (UnknownHostException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					
				}
				
			}
			
		});
		
	}
}
