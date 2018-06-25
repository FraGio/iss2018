package it.unibo.pfrs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.json.JSONObject;

public class mbotConnTcp {
	private static String hostName= "localhost";
	private static int port = 8999;
	private static String sep=";";
 	protected static Socket clientSocket ;
	protected static PrintWriter outToServer;
	protected static BufferedReader inFromServer;
	 
	public mbotConnTcp() {
		try {
			initClientConn();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	protected static void initClientConn() throws Exception {
		 clientSocket = new Socket(hostName, port);
		 outToServer  = new PrintWriter(clientSocket.getOutputStream());
		 inFromServer= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public static void sendCmd(String msg) throws Exception {
		if( outToServer == null ) return;
		String jsonString = "{ 'type': '" + msg + "', 'arg': 800 }";
		JSONObject jsonObject = new JSONObject(jsonString);
		msg = sep+jsonObject.toString()+sep;
		System.out.println("sending msg=" + msg);
		outToServer.println(msg);
		outToServer.flush();
	}

	protected void println(String msg) {
		System.out.println(  msg);
	}

 	
	public static void mbotForward() {
 		try {  sendCmd("moveForward"); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotBackward() {
		try { sendCmd("moveBackward"); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotLeft() {
		try { sendCmd("turnLeft"); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotRight(  ) {
		try { sendCmd("turnRight"); } catch (Exception e) {e.printStackTrace();}
	}
	public static void mbotStop() {
		try { sendCmd("alarm"); } catch (Exception e) {e.printStackTrace();}
	}
  	
//Just for testing
	public static void main(String[] args)   {
		String line = null;
		try {
			initClientConn();
			System.out.println("STARTING ... ");
			mbotForward();
			
			while((line = inFromServer.readLine()) != null)
				System.out.println(line);
			
//			Thread.sleep(1000);
//			mbotBackward();
//			Thread.sleep(1000);
//			mbotLeft() ;
//			Thread.sleep(1000);
//			mbotForward();
//			Thread.sleep(1000);
//			 mbotRight(  ) ;
//			Thread.sleep(1000);
//			mbotForward();
//			Thread.sleep(1000);
//			mbotStop();
			System.out.println("END");
		} catch (Exception e) {
 			e.printStackTrace();
		}
}	
	
 }
