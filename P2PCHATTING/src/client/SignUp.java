package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import Util.XMLGen;
import basic.UserAccount;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JPasswordField txtPass;
	private JTextField txtPort;
	private String userName;
	private String passWord;
	private int port;
	PrintWriter writer;
	private Socket sock;
	BufferedReader reader ;
	private JTextField txtIP;
	private JTextField txtServerPort;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel label = new JLabel("Username");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label, 30, SpringLayout.NORTH, contentPane);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Password");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label_1, 34, SpringLayout.SOUTH, label);
		sl_contentPane.putConstraint(SpringLayout.EAST, label_1, 0, SpringLayout.EAST, label);
		contentPane.add(label_1);
		
		txtName = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.EAST, txtName, -160, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, label, -59, SpringLayout.WEST, txtName);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtName, -3, SpringLayout.NORTH, label);
		txtName.setColumns(10);
		contentPane.add(txtName);
		
		JButton btCreate = new JButton("Create");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btCreate, 181, SpringLayout.NORTH, contentPane);
		btCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String pass = txtPass.getText();
				String IPServer = txtIP.getText();
				int port = Integer.parseInt(txtPort.getText());
				int serverPort = Integer.parseInt(txtServerPort.getText());
				UserAccount usr = new UserAccount(name,pass);
				String s = XMLGen.genREG(usr);
				try {
					Socket e1 = new Socket(IPServer,serverPort);
					PrintWriter pr = new PrintWriter(new DataOutputStream(e1.getOutputStream()));
					BufferedReader is= new BufferedReader(new InputStreamReader(e1.getInputStream()));
					pr.println(s);
					pr.flush();
					String result = is.readLine();
					if (result.contains(XMLGen.genREG_RESULT(1))==true)
					{
						JOptionPane.showMessageDialog(null, "Sign up fail");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Sign up sucess");
					}
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Ket noi toi server thoi !!!
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btCreate, 73, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btCreate, -34, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btCreate, -256, SpringLayout.EAST, contentPane);
		contentPane.add(btCreate);
		
		JButton btExit = new JButton("Exit");
		btExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btExit, -2, SpringLayout.NORTH, btCreate);
		sl_contentPane.putConstraint(SpringLayout.WEST, btExit, 63, SpringLayout.EAST, btCreate);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btExit, -31, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btExit, -107, SpringLayout.EAST, contentPane);
		contentPane.add(btExit);
		
		txtPass = new JPasswordField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtPass, -3, SpringLayout.NORTH, label_1);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtPass, 0, SpringLayout.WEST, txtName);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtPass, -160, SpringLayout.EAST, contentPane);
		contentPane.add(txtPass);
		
		JLabel label_2 = new JLabel("Port");
		sl_contentPane.putConstraint(SpringLayout.WEST, label_2, 0, SpringLayout.WEST, label);
		contentPane.add(label_2);
		
		txtPort = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, label_2, 3, SpringLayout.NORTH, txtPort);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, label_2, 17, SpringLayout.NORTH, txtPort);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtPort, -31, SpringLayout.NORTH, btExit);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtPort, 0, SpringLayout.EAST, txtName);
		txtPort.setColumns(10);
		contentPane.add(txtPort);
		
		txtIP = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtIP, 0, SpringLayout.SOUTH, txtName);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtIP, -21, SpringLayout.EAST, contentPane);
		txtIP.setColumns(10);
		contentPane.add(txtIP);
		
		txtServerPort = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtServerPort, 48, SpringLayout.SOUTH, txtIP);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtServerPort, 0, SpringLayout.WEST, txtIP);
		txtServerPort.setColumns(10);
		contentPane.add(txtServerPort);
		
		JLabel lblIpServer = new JLabel("IP server");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblIpServer, -6, SpringLayout.NORTH, txtIP);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblIpServer, -60, SpringLayout.EAST, contentPane);
		contentPane.add(lblIpServer);
		
		JLabel lblPort = new JLabel("Port");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPort, 17, SpringLayout.SOUTH, txtIP);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPort, 0, SpringLayout.WEST, txtIP);
		contentPane.add(lblPort);
	}

}
