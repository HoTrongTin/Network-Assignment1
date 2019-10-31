package client;

import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
import java.net.*;  
import java.io.*;  

public class SendFile extends JFrame implements ActionListener ,Runnable{  
   
    private JTextField txtFile;  
    private String IPAddress;
    private int port;
     public SendFile(String IPAddress,int port){
    	 this.port = port;
     }
   
    public void Display(){  
   
        JFrame frame = new JFrame();  
        frame.setTitle("Client");  
   
        FlowLayout layout = new FlowLayout();  
        layout.setAlignment(FlowLayout.LEFT);  
   
        JLabel lblFile = new JLabel("Filename:");  
         
        txtFile = new JTextField();  
        txtFile.setPreferredSize(new Dimension(150,30));  
   
        JButton btnTransfer = new JButton("Transfer");  
        btnTransfer.addActionListener(this);  
   
        JPanel mainPanel = new JPanel();  
        mainPanel.setLayout(layout);  
        mainPanel.add(lblFile);  
        mainPanel.add(txtFile);  
        mainPanel.add(btnTransfer);  
   
        frame.getContentPane().add(mainPanel);  
        frame.pack();  
        frame.setVisible(true);  
    }  
   
    public void actionPerformed(ActionEvent e) {  
 
        JFileChooser fileDlg = new JFileChooser();  
        fileDlg.showOpenDialog(this);  
        String filename = fileDlg.getSelectedFile().getAbsolutePath();  
        txtFile.setText(filename);  
   
        try{  
   
           
            Socket sk = new Socket(IPAddress, port);  
            OutputStream output = sk.getOutputStream();  
   
            /* Send filename to server */  
   
            OutputStreamWriter outputStream = new OutputStreamWriter(sk.getOutputStream());  
           
            BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));  
   
           
                FileInputStream file = new FileInputStream(filename);  
   
                byte[] buffer = new byte[sk.getSendBufferSize()];  
   
                int bytesRead = 0;  
   
                while((bytesRead = file.read(buffer))>0)  
                {  
                    output.write(buffer,0,bytesRead);  
                }  
   
                output.close();  
                file.close();  
                sk.close();  
   
                JOptionPane.showMessageDialog(this, "Transfer complete");  
             
        }  
        catch (Exception ex){  
            /* Catch any errors */  
            JOptionPane.showMessageDialog(this, ex.getMessage());  
        }  
    }

	@Override
	public void run() {
		 SendFile clientForm = new SendFile(IPAddress,port);  
	       clientForm.Display(); 
	}  
}  
   
   
