package client;


import javax.swing.JFrame;

import javax.swing.SpringLayout;

import Util.XMLGen;

import javax.swing.JButton;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import javax.swing.JTextField;
import java.awt.event.ActionListener;

import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

public class ChatFrame extends JFrame {
	public JTextField txtMessage;
	public JTextArea txtConversation;
	public JButton btnSendMess;
	public JButton btnSendFile;
	public JButton btnEndChat;
	private ChatFrame chatBox;
	private String ipadress;
	private int port;
	
	//Lenh mo Frame tu ClientActiveChat
	public ChatFrame(boolean isActivechat, Socket socket,String friendName) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				//Gui thong bao toi Sever
				SendCommand sendComamnd = new SendCommand(socket, XMLGen.genCHAT_CLOSE());
				new Thread(sendComamnd).start();
				
			}
		});
		if (isActivechat==true)
			this.setTitle("You is chatting with " + friendName + " [Active chat]");
		else
			this.setTitle("You is chatting with " + friendName +  " [Passive chat]");
		this.chatBox = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 487);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		txtMessage = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, txtMessage, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtMessage, -32, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtMessage, -198, SpringLayout.EAST, getContentPane());
		getContentPane().add(txtMessage);
		txtMessage.setColumns(10);
		
		 btnSendMess = new JButton("Send Mess");
		 springLayout.putConstraint(SpringLayout.EAST, btnSendMess, -55, SpringLayout.EAST, getContentPane());
		 springLayout.putConstraint(SpringLayout.NORTH, txtMessage, 22, SpringLayout.SOUTH, btnSendMess);
		 springLayout.putConstraint(SpringLayout.SOUTH, btnSendMess, -85, SpringLayout.SOUTH, getContentPane());
		 springLayout.putConstraint(SpringLayout.NORTH, btnSendMess, 316, SpringLayout.NORTH, getContentPane());
		btnSendMess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isActivechat == false)
				{
					ClientActiveSend sendThread = new ClientActiveSend(socket, txtMessage.getText(), chatBox);
					Thread thread = new Thread(sendThread);
					thread.start();
					txtMessage.setText("");
				}
				else
				{
					ClientNegativeSend sendThread = new ClientNegativeSend(socket, txtMessage.getText() , chatBox);
					Thread thread = new Thread(sendThread);
					thread.start();
					txtMessage.setText("");
				}
			}
		});
		getContentPane().add(btnSendMess);
		
		 btnSendFile = new JButton("Send File");
		 btnSendFile.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 		new Thread(new SendCommand(socket, XMLGen.genFILE_REQ("dd"))).start();
		 	}
		 });
		 springLayout.putConstraint(SpringLayout.SOUTH, btnSendFile, -32, SpringLayout.NORTH, btnSendMess);
		 springLayout.putConstraint(SpringLayout.EAST, btnSendFile, -55, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnSendFile);
		
		 btnEndChat = new JButton("End chat");
		 springLayout.putConstraint(SpringLayout.EAST, btnEndChat, -55, SpringLayout.EAST, getContentPane());
		 springLayout.putConstraint(SpringLayout.NORTH, btnSendFile, 29, SpringLayout.SOUTH, btnEndChat);
		 springLayout.putConstraint(SpringLayout.SOUTH, btnEndChat, -241, SpringLayout.SOUTH, getContentPane());
		 springLayout.putConstraint(SpringLayout.NORTH, btnEndChat, 152, SpringLayout.NORTH, getContentPane());
		 btnEndChat.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {

				SendCommand sendComamnd = new SendCommand(socket, XMLGen.genCHAT_CLOSE());
				new Thread(sendComamnd).start();
				
					
		 	}
		 });
		getContentPane().add(btnEndChat);
		
		
		
		
		txtConversation= new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, txtConversation, 23, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtConversation, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtConversation, -119, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtConversation, -107, SpringLayout.WEST, btnSendMess);
		getContentPane().add(txtConversation);
		txtConversation.setLineWrap(true);
		txtConversation.setEditable(false);
		JScrollPane scroll = new JScrollPane (txtConversation, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		springLayout.putConstraint(SpringLayout.WEST, btnEndChat, 56, SpringLayout.EAST, scroll);
		springLayout.putConstraint(SpringLayout.WEST, btnSendFile, 56, SpringLayout.EAST, scroll);
		springLayout.putConstraint(SpringLayout.WEST, btnSendMess, 56, SpringLayout.EAST, scroll);
		springLayout.putConstraint(SpringLayout.EAST, scroll, -383, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, scroll, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scroll, -85, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scroll, 0, SpringLayout.WEST, getContentPane());
		getContentPane().add(scroll);
		
	}
}
