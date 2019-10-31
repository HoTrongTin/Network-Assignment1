package client;

import java.net.Socket;

import javax.swing.plaf.SliderUI;

import client.*;


import Util.XMLGen;

public class UpdateStatusThread implements Runnable
{
	private Socket socket;
	private String username;
	public UpdateStatusThread(Socket socket,String username) {
		this.socket= socket;
		this.username = username;
	}
	@Override
	public void run() {
		while (true)
		{
			System.out.println("Update status to server");
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
			SendCommand send= new SendCommand(socket,XMLGen.genKEEP_ALIVE(username, 0));
			new Thread(send).start();
		}
		
	}
	
}