package messagePosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread{
	
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	int portNo;
	List<Message>  Records = new ArrayList<Message>();
	List<String> knownUsers = new ArrayList<String>();
	List<String> ccUsers = new ArrayList<String>();
	
	ServerThread(int portNo)
	{
	
		
		this.portNo = portNo;	
	}
	@Override
	public void run()
	{
		try {
			
			//providerSocket = new ServerSocket(2007,10);
			providerSocket = new ServerSocket(portNo,10);
			System.out.println("Server is running on "+InetAddress.getLocalHost().getHostName());
			connection = providerSocket.accept();
			//System.out.println("Connection made by unknown user ");
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			//3. get Input and Output streams
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			Message m= new Message();
			while(true)
			{
				m =(Message) in.readObject();
				Records.add(m);
				if(m.message!=null &&m.message.equals("connection"));
				{
					System.out.println("Connection made by known user "+m.userName);
					if(ccUsers !=null && !ccUsers.contains(m.userName) )
					ccUsers.add(m.userName);
					//Main.ccUsers.add(m.userName);
					if(knownUsers !=null && !knownUsers.contains(m.userName) )
					knownUsers.add(m.userName);
					//Main.knownUsers.add(m.userName);
				}
				//m =  (Message)in.readObject();
				if(m.action == 1)
				{
					System.out.println(m.userName +" lists all known users");
					out.writeObject(knownUsers);
					out.flush();
				}
				 else if(m.action ==2 )
					{
						System.out.println(m.userName +" lists all curently connected users");
						out.writeObject(ccUsers);
						out.flush();
					}
				//m =  (Message)in.readObject();
				//System.out.println("Reads here");
				else if(m.action ==3)
				{
					
					if(!ccUsers.contains(m.recepient))	
					{
						knownUsers.add(m.recepient);
						Records.add(m);
						System.out.println(m.userName + " posts a message for" +m.recepient);
					}
				}
				//m =  (Message)in.readObject();
				//System.out.println("Reads here");
				else if(m.action ==4)
				{
					 m.recepients = new ArrayList<String>(ccUsers);
					 
					 Records.add(m);
					 System.out.println(m.userName +" posted a message for all currently connected users" );
				}
				 //m =  (Message)in.readObject();
				 else if(m.action ==5)
				{
					 m.recepients = new ArrayList<String>(knownUsers);
					 Records.add(m);
					 System.out.println(m.userName +" posted a message for all known users" );
				}
				//m =  (Message)in.readObject();
				
				 else if (m.action == 6)
				 {
					 List<Message> msgs = new ArrayList<Message>();
					 System.out.println(m.userName +" tries to get messages");
					 for(int y=0;y<Records.size();y++)
					 {
						 if(Records.get(y).recepient!=null && Records.get(y).recepient.equals(m.userName) || Records.get(y).recepients!=null && Records.get(y).recepients.contains(m.userName))
						 {
							 msgs.add(Records.get(y));
						 }
						 
					 }
					 
					 out.writeObject(msgs);
					 out.flush();
				 }
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//2. Wait for connection
		System.out.println("Waiting for connection");
		
	}
	public class ListenerThread extends Thread
	{
		Message m;
		@Override
		public void run()
		{
	try {
		m=(Message) in.readObject();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(m.action ==3)
	{
		if(!ccUsers.contains(m.recepient))	
		{
			knownUsers.add(m.recepient);
			Records.add(m);
			System.out.println(m.userName + "posts a message for" +m.recepient);
		}
	}
	}
	}
}
