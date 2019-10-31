package client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Util.XMLGen;

import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class RecieveFileFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtPort;
	private JTextField txtFileURL;
	private File file;
	private int port;
	public RecieveFileFrame(Socket socket) {
		this.file = null;
		this.port = -1;
		setTitle("Save file");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JButton btnNewButton = new JButton("Browse file");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnNewButton, 41, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnNewButton, -44, SpringLayout.EAST, contentPane);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				 
				int userSelection = fileChooser.showSaveDialog(contentPane);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    file = fileChooser.getSelectedFile();
				    System.out.println("Save file as: " + file.getAbsolutePath());
				    txtFileURL.setText(file.getPath());
				}
			}
		});
		contentPane.add(btnNewButton);
		
		txtPort = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.EAST, txtPort, -139, SpringLayout.EAST, contentPane);
		contentPane.add(txtPort);
		txtPort.setColumns(10);
		
		JLabel lblSaveUrl = new JLabel("Save URL");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSaveUrl, 4, SpringLayout.NORTH, btnNewButton);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSaveUrl, 0, SpringLayout.WEST, contentPane);
		contentPane.add(lblSaveUrl);
		
		JButton btnAccept = new JButton("Accept");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnAccept, 54, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnAccept, -10, SpringLayout.SOUTH, contentPane);
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					
					 port = Integer.parseInt(txtPort.getText());
					 if (file != null && port != -1)
						{
							RecieveFile threadRecieve = new RecieveFile(port, file);
							new Thread(threadRecieve).start();
							SendCommand sendCommand = new SendCommand(socket, XMLGen.genFILE_REQ_ACK(port));
						 	new Thread(sendCommand).start();
							
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Khong the accept !");
						}
					
				}
					catch(Exception ex)
					{
						port = -1;
					}
				
			}
		});
		contentPane.add(btnAccept);
		
		JButton btnDecline = new JButton("Decline");
		btnDecline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				SendCommand sendCommand = new SendCommand(socket, XMLGen.genFILE_REQ_NOACK());
				new Thread(sendCommand).start();
				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnDecline, 0, SpringLayout.NORTH, btnAccept);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnDecline, 76, SpringLayout.EAST, btnAccept);
		contentPane.add(btnDecline);
		
		JLabel lblPort = new JLabel("Port");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPort, 26, SpringLayout.SOUTH, lblSaveUrl);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtPort, 41, SpringLayout.EAST, lblPort);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPort, 0, SpringLayout.WEST, lblSaveUrl);
		contentPane.add(lblPort);
		
		txtFileURL = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.EAST, txtFileURL, -144, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtPort, 24, SpringLayout.SOUTH, txtFileURL);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnNewButton, 11, SpringLayout.EAST, txtFileURL);
		txtFileURL.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtFileURL, 1, SpringLayout.NORTH, btnNewButton);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtFileURL, 0, SpringLayout.WEST, txtPort);
		contentPane.add(txtFileURL);
		txtFileURL.setColumns(10);
	}
}
