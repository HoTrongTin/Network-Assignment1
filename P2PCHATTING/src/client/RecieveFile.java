package client;

//Server.java  

/*  
* Server waits for a connection to be established by client  
*  
*/  

import java.io.*;  
import java.net.*;

import javax.swing.JOptionPane;  

public class RecieveFile  implements Runnable
{ 	
	private File file;
	private int port ;
	public  RecieveFile(int port,File  file) {
		this.file = file;
		this.port = port;
	}
@Override
	public void run() {
	System.out.println("Server receive file is running...");  
    ServerSocket server;
	try {
		System.out.println("Server receive file is trying...");
		server = new ServerSocket(port);
		System.out.println("Server receive file is trying hard...");
		Socket sk = server.accept();  
 		System.out.println("Server accepted client");  
 		InputStream input = sk.getInputStream();  
 		BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));  
 		BufferedWriter outReader = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));  
 		//System.out.println("Gui file nay nhan" + file.getAbsolutePath());
 		FileOutputStream wr = new FileOutputStream(file);

 		byte[] buffer = new byte[sk.getReceiveBufferSize()];  
 		
 		int bytesReceived = 0;  

 		while((bytesReceived = input.read(buffer))>0)  
 		{  
       
 			wr.write(buffer,0,bytesReceived);  
 		}  
 		JOptionPane.showMessageDialog(null, "File trasfer completed!");
	} catch (IOException e)
	{
		
	}
	
} 

} 