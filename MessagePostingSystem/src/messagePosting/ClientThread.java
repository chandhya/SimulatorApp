package messagePosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientThread extends Thread{
	
	String machineType;
	String nodeName;
	int portNo;
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
	ClientThread(String machineType,String nodeName,int portNo)
	{ 
		this.machineType = machineType;
		this.nodeName = nodeName;
		this.portNo = portNo;
	}
	
	
@SuppressWarnings("unchecked")
@Override
public void run()
{
	try {
		requestSocket = new Socket(InetAddress.getLocalHost().getHostName(), 2009);
		//requestSocket = new Socket("localhost", 2007);
		System.out.println("Connecting to "+nodeName+ ": "+portNo);
		
		out = new ObjectOutputStream(requestSocket.getOutputStream());
		
		in = new ObjectInputStream(requestSocket.getInputStream());
		System.out.println("Enter your name :");
		
		Scanner sn = new Scanner(System.in);
		String userName  = sn.nextLine();
		Message msg = new Message();
		msg.userName = userName;
		msg.UserType = "cc";
		msg.message ="Connection";
		msg.recepient = "server";
		out.writeObject(msg);
		out.flush();
		while(true)
		{
		System.out.println("Enter your choice");
		System.out.println("1. Display the names of all known users");
		System.out.println("2. Display the names of all currently connected users");
		System.out.println("3. Send a text message to a particular user");
		System.out.println("4. Send a text message to all currently connected users");
		System.out.println("5. Send a text message to all known users");
		System.out.println("6. Get my messages");
		System.out.println("7. Exit");

		int choice =sn.nextInt();
		Message m;
		List<String> msgList = new ArrayList<String>();
		
		if(choice == 1)
		{
		
			m = new Message();
			m.message = "display known";
			m.action = 1;
			m.userName = userName;
			m.recepient = "server";
			m.UserType = "cc";
			out.writeObject(m);
			out.flush();
			try {
				msgList = (ArrayList<String>)in.readObject();
				
				System.out.println("Known Users:");
				if(msgList.size()>0)
				{
					for(int i =0;i<msgList.size();i++)
					{
						System.out.println(msgList.get(i));
					}
				}
				else
					System.out.println("No known users");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (choice == 2)
		{
		
			m = new Message();
			m.action = 2;
			m.userName = userName;
			m.recepient = "server";
			m.UserType = "cc";
			out.writeObject(m);
			out.flush();
			try {
				msgList = (List<String>)in.readObject();
				
				System.out.println("Currently connected Users:");
				if(msgList.size()>0)
				{
					for(int i =0;i<msgList.size();i++)
					{
						System.out.println(msgList.get(i));
					}
				}
				else
					System.out.println("No known users");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(choice ==3)
		{
			m = new Message();
			System.out.println("Enter recepient's name");
			m.recepient = sn.next();
			m.userName = userName;
			System.out.println("Enter a message");
			m.message = sn.next();
			m.action =3;
			out.writeObject(m);
			out.flush();
			//Thread lt = new ServerThread();
			
			//lt.start();
			System.out.println("Message posted to" +m.recepient);
			
		}
		else if(choice ==4)
		{
			m = new Message();
			System.out.println("Enter a message to send to all currently connected users");
			m.message = sn.next();
			m.action = 4;
			m.userName = userName;
			//m.recepients = new ArrayList<String>();
			m.req = "ccUsers";
			out.writeObject(m);
			System.out.println("Message posted to all currently connected Users" );
		
		}
		else if(choice ==5)
		{	m = new Message();
			System.out.println("Enter a message to send to all known users");
			m.message = sn.next();
			m.action = 5;
			m.userName = userName;
			//m.recepients = new ArrayList<String>();
			m.req = "knownUsers";
			out.writeObject(m);
			System.out.println("Message posted to all known Users" );
		}	
		
		else if(choice ==6)
		{
			m= new Message();
			m.action =6;
			m.userName = userName;
			out.writeObject(m);
			List<Message> msg2 = new ArrayList<Message>();
			try {
				msg2 = (List<Message>)in.readObject();
				System.out.println("Your messages :");
				for(int t=0;t<msg2.size();t++)
				{
				System.out.println(msg2.get(t).userName +" says "+msg2.get(t).message);
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			else if(choice ==7)
			break;
		
	}
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


}
