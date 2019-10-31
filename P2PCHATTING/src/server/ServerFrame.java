package server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;


public class ServerFrame {

	private JFrame frmPpChattingServer;
	private JTextField txtPort;
	private JButton btnStartServer;
	private JButton btnChoosefile;
	public JFileChooser fileChooser;
	public JTextArea txtCommand;
	private JLabel txtURL;
	public String filePath =  "D://data.xml";
	public int port;
	
	private SpringLayout springLayout;
	private JLabel lblDatabaseFile;
	private JLabel lblServerPort;
	private JTextArea textArea;
	private JButton btnStartServer_1;
	private JButton btnChooseDatabase;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerFrame window = new ServerFrame();
					window.frmPpChattingServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerFrame() {
		
		initialize();
		fileChooser = new JFileChooser();
		textArea = new JTextArea();
		
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 32, SpringLayout.SOUTH, lblServerPort);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 24, SpringLayout.WEST, frmPpChattingServer.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -100, SpringLayout.SOUTH, frmPpChattingServer.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textArea, 296, SpringLayout.WEST, frmPpChattingServer.getContentPane());
		textArea.setLineWrap(true);
		this.txtCommand = textArea;
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -60, SpringLayout.EAST, frmPpChattingServer.getContentPane());
		scrollPane.setViewportView(textArea);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 26, SpringLayout.SOUTH, lblServerPort);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 29, SpringLayout.WEST, frmPpChattingServer.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -51, SpringLayout.SOUTH, frmPpChattingServer.getContentPane());
		frmPpChattingServer.getContentPane().add(scrollPane);
		txtCommand.setEditable(false);
		
		JButton btnClearLog = new JButton("Clear LOG");
		springLayout.putConstraint(SpringLayout.NORTH, btnClearLog, -4, SpringLayout.NORTH, lblServerPort);
		springLayout.putConstraint(SpringLayout.WEST, btnClearLog, 53, SpringLayout.EAST, btnStartServer_1);
		btnClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		frmPpChattingServer.getContentPane().add(btnClearLog);
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmPpChattingServer = new JFrame();
		frmPpChattingServer.setTitle("P2P CHATTING SERVER");
		frmPpChattingServer.setBounds(100, 100, 727, 475);
		frmPpChattingServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		springLayout = new SpringLayout();
		frmPpChattingServer.getContentPane().setLayout(springLayout);
		
		btnChooseDatabase = new JButton("Choose database");
		springLayout.putConstraint(SpringLayout.EAST, btnChooseDatabase, -257, SpringLayout.EAST, frmPpChattingServer.getContentPane());
		this.btnChoosefile = btnChooseDatabase;
		btnChooseDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile(e);
			}
		});
		frmPpChattingServer.getContentPane().add(btnChooseDatabase);
		
		lblDatabaseFile = new JLabel("Database file");
		springLayout.putConstraint(SpringLayout.NORTH, btnChooseDatabase, -4, SpringLayout.NORTH, lblDatabaseFile);
		springLayout.putConstraint(SpringLayout.WEST, lblDatabaseFile, 10, SpringLayout.WEST, frmPpChattingServer.getContentPane());
		frmPpChattingServer.getContentPane().add(lblDatabaseFile);
		
		JLabel txtURL = new JLabel("No file chosen");
		springLayout.putConstraint(SpringLayout.NORTH, txtURL, 0, SpringLayout.NORTH, lblDatabaseFile);
		springLayout.putConstraint(SpringLayout.WEST, txtURL, 18, SpringLayout.EAST, lblDatabaseFile);
		springLayout.putConstraint(SpringLayout.EAST, txtURL, 372, SpringLayout.EAST, lblDatabaseFile);
		frmPpChattingServer.getContentPane().add(txtURL);
		this.txtURL = txtURL;
		lblServerPort = new JLabel("Server port");
		springLayout.putConstraint(SpringLayout.NORTH, lblServerPort, 50, SpringLayout.NORTH, frmPpChattingServer.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblDatabaseFile, -17, SpringLayout.NORTH, lblServerPort);
		springLayout.putConstraint(SpringLayout.WEST, lblServerPort, 0, SpringLayout.WEST, lblDatabaseFile);
		frmPpChattingServer.getContentPane().add(lblServerPort);
		
		txtPort = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, txtPort, 25, SpringLayout.EAST, lblServerPort);
		springLayout.putConstraint(SpringLayout.SOUTH, txtPort, 0, SpringLayout.SOUTH, lblServerPort);
		frmPpChattingServer.getContentPane().add(txtPort);
		txtPort.setColumns(10);
		
		btnStartServer_1 = new JButton("Start server");
		springLayout.putConstraint(SpringLayout.NORTH, btnStartServer_1, -4, SpringLayout.NORTH, lblServerPort);
		springLayout.putConstraint(SpringLayout.WEST, btnStartServer_1, 0, SpringLayout.WEST, btnChooseDatabase);
		btnStartServer_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startServer(e);
			}
		});
		this.btnStartServer = btnStartServer_1;
		btnStartServer_1.setEnabled(false);
		frmPpChattingServer.getContentPane().add(btnStartServer_1);
	}
	private void startServer(java.awt.event.ActionEvent evt)
	{	
		try
		{
			port = Integer.parseInt(txtPort.getText());
			btnStartServer.setEnabled(false);
			btnChoosefile.setEnabled(false);
			
			Thread thread = new Thread(new Server(this));
			thread.start();
		}
		catch( NumberFormatException e)
		{
			txtPort.setText("");
			JOptionPane.showMessageDialog(null, "Please re enter the port number");
		}
		
		
		
	}
	private void chooseFile(java.awt.event.ActionEvent evt) {
		
		JFileChooser chooser= new JFileChooser();

		int choice = chooser.showOpenDialog(fileChooser);

		if (choice != JFileChooser.APPROVE_OPTION) return;

		File chosenFile = chooser.getSelectedFile();
		if (chosenFile != null)
		{
			 filePath = chosenFile.getPath();
	            if(this.isWin32()){ filePath = filePath.replace("\\", "/"); }
	            txtURL.setText(filePath);
	            btnStartServer.setEnabled(true);;
		}
		
	  }
	 public boolean isWin32(){
	        return System.getProperty("os.name").startsWith("Windows");
	    }
}
