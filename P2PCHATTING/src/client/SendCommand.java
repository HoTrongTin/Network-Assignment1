package client;

import java.io.PrintWriter;
import java.net.Socket;

public class SendCommand implements Runnable
{
	Socket socket;
	String command;
	public SendCommand(Socket socket, String command)
	{
		this.socket = socket;
		this.command = command;
	}
	@Override
	public void run() {
		try{
			
			if (socket.isClosed() ==false)
			{
				
				PrintWriter pwPrintWriter  = new PrintWriter(socket.getOutputStream(), true);	
				pwPrintWriter.println(command);
				pwPrintWriter.flush();
			
			}
			else
			{
				
				System.out.println("Khong the ket noi den client da gui yeu cau!");
			}
			
			
			}
			catch(Exception ex){System.out.println(ex.getMessage());}	
		}
	
	
}
