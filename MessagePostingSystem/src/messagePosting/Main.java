package messagePosting;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static String machineType;
	public static String nodeName;
	public static int portNo;
	Socket requestSocket;
	public static List<Message>  Records = new ArrayList<Message>();
	//public static List<String> knownUsers = new ArrayList<String>();
	//public static List<String> ccUsers = new ArrayList<String>();
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
 	
	public static void main(String args[])
	{
		//nodeName = "localhost";
		//portNo = 1923;
		//machineType = "client";
		machineType = "client";
		
		try {
			nodeName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		portNo = 2009;
		initiateProcess(machineType);
		
	}
	private static void initiateProcess(String machineType2) {
		//Initiates the prce
		
		//if(machineType.equals("client"))
			//{
			Thread client = new ClientThread(machineType,nodeName,portNo);
			client.start();
			//}
		//else if(machineType.equals("server"))
		//{
			Thread server = new ServerThread(portNo);
			server.start();
		//}
		
	}

}
