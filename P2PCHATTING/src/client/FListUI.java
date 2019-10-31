package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;


import Util.XMLGen;
import Util.XMLParseTool;
import basic.Friend;
import basic.Protocol;

import java.net.*;
import java.nio.file.ClosedFileSystemException;
import java.util.ArrayList;
import java.io.*;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class FListUI extends JFrame {

	private static final long serialVersionUID = 1L;
	public String socketIP;
	public int port;
	public  Socket socc;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtPort;
	private JTextField textIP;
    private JLabel lblOnlineFriend;
    private JButton btnSignOut;
    public String userName;
	public int userConnectionPort;

	private List rowList;
	
	//Tu Login
	public FListUI(Socket socc,ArrayList<Friend> frList,String userName,int userConnectionPort, String socketIP,int port) {
		this.userName = userName;
		this.socketIP = socketIP;
		this.userConnectionPort = userConnectionPort;
		this.port = port;
		
		ClientNegativeChat clientNegativeChat = new ClientNegativeChat(userConnectionPort);
		new Thread(clientNegativeChat).start();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.socc = socc;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblUsername = new JLabel("Username");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUsername, 31, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUsername, 10, SpringLayout.WEST, contentPane);
		contentPane.add(lblUsername);
		
		JLabel lblPort = new JLabel("Server Port");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPort, 0, SpringLayout.WEST, lblUsername);
		contentPane.add(lblPort);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblIpAddress, 98, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblPort, -18, SpringLayout.NORTH, lblIpAddress);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblIpAddress, 0, SpringLayout.WEST, lblUsername);
		contentPane.add(lblIpAddress);
		
		txtName = new JTextField();
		txtName.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtName, 0, SpringLayout.NORTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtName, 31, SpringLayout.EAST, lblUsername);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtPort, 0, SpringLayout.NORTH, lblPort);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtPort, 0, SpringLayout.EAST, txtName);
		contentPane.add(txtPort);
		txtPort.setColumns(10);
		
		textIP = new JTextField();
		textIP.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textIP, 0, SpringLayout.NORTH, lblIpAddress);
		sl_contentPane.putConstraint(SpringLayout.EAST, textIP, 0, SpringLayout.EAST, txtName);
		contentPane.add(textIP);
		textIP.setColumns(10);
		
		JButton btnChat = new JButton("Chat");
		btnChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String friendName = txtName.getText();
				int port = Integer.parseInt(txtPort .getText());
				String IPaddress = textIP.getText();
			//	IPaddress = "localhost";
				ClientActiveChat active = new ClientActiveChat(friendName,userName, IPaddress, port);
				new Thread(active).start();
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnChat, 49, SpringLayout.SOUTH, textIP);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnChat, 37, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnChat, -307, SpringLayout.EAST, contentPane);
		contentPane.add(btnChat);
	//	this.socc = socc;
		
		//rowList = FriendListUI.rowList;
		
		
		lblOnlineFriend = new JLabel("Online Friend");
		sl_contentPane.putConstraint(SpringLayout.EAST, lblOnlineFriend, -66, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblOnlineFriend, 10, SpringLayout.NORTH, contentPane);
		contentPane.add(lblOnlineFriend);
		
		btnSignOut = new JButton("Sign out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SendCommand sendCommand = new SendCommand(socc, XMLGen.genKEEP_ALIVE(userName, 1));
				new Thread(sendCommand).run();
				 System.exit(0);
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnSignOut, 0, SpringLayout.NORTH, btnChat);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnSignOut, 44, SpringLayout.EAST, btnChat);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnSignOut, -183, SpringLayout.EAST, contentPane);
		contentPane.add(btnSignOut);
		
		 rowList = new List();
		rowList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (rowList.getSelectedIndex() < frList.size()){
				txtName.setText(frList.get(rowList.getSelectedIndex()).getAccountName());
				textIP.setText(frList.get(rowList.getSelectedIndex()).getIPAdress());
				txtPort.setText(String.valueOf(frList.get(rowList.getSelectedIndex()).getPort()));
				}
			}
		});
		rowList.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (rowList.getSelectedIndex() < frList.size()){
					txtName.setText(frList.get(rowList.getSelectedIndex()).getAccountName());
					textIP.setText(frList.get(rowList.getSelectedIndex()).getIPAdress());
					txtPort.setText(String.valueOf(frList.get(rowList.getSelectedIndex()).getPort()));
					}
				
			}
		});
		rowList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rowList.getSelectedIndex() < frList.size()){
					txtName.setText(frList.get(rowList.getSelectedIndex()).getAccountName());
					textIP.setText(frList.get(rowList.getSelectedIndex()).getIPAdress());
					txtPort.setText(String.valueOf(frList.get(rowList.getSelectedIndex()).getPort()));
					}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, rowList, 14, SpringLayout.SOUTH, lblOnlineFriend);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, rowList, 140, SpringLayout.SOUTH, lblOnlineFriend);
		sl_contentPane.putConstraint(SpringLayout.EAST, rowList, -23, SpringLayout.EAST, contentPane);
		for(int i=0;i< frList.size();i++){
    		rowList.add(frList.get(i).getAccountName());
    	}
		contentPane.add(rowList);
		
		JButton btnRefreshlist = new JButton("RefreshList");
		btnRefreshlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshList();
			}
		});
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnRefreshlist, -31, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnRefreshlist, -43, SpringLayout.EAST, contentPane);
		contentPane.add(btnRefreshlist);
		
		contentPane.setVisible(true);
		
		
	}
	public void setList(ArrayList<Friend> list)
	{
		rowList.removeAll();
		for(int i=0;i< list.size();i++){
    		rowList.add(list.get(i).getAccountName());
    	}
	}
	public void refreshList()
	{
		try{
		PrintWriter writer1 = new PrintWriter(new DataOutputStream(socc.getOutputStream()));
        writer1.println(XMLGen.genGET_FRIEND_LIST());
        writer1.flush(); 	
		BufferedReader buffer1;
		
			buffer1 = new BufferedReader(new InputStreamReader(socc.getInputStream()));
		
			String linee = buffer1.readLine();
			if (XMLParseTool.isXMLCode(linee))
			{
				Document doc = XMLParseTool.genXMLDocument(linee);
			
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
									setList(list);
								}		
							}
						}
					}
				}
				
			
		
			
		}
		catch (Exception ex)
		{
			
		}
	
		}
}
