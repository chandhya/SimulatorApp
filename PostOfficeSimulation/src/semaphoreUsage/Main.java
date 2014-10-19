package semaphoreUsage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Main extends Thread{
	//List of Tasks
	public static String task_1 = "Buys Stamps";
	public static String task_2 = "Mail a letter";
	public static String task_3 = "Mail a package";
	//List of Semaphores
	public static Semaphore[] Max_Capacity = new Semaphore[10];
	public static Semaphore Max_Count = new Semaphore(0); 
	public static Semaphore Postal_Worker =  new Semaphore(0);
	public static Semaphore NextInQueue = new Semaphore(0);
	public static Semaphore PostalWorkerReady = new Semaphore(0); 
	public static Semaphore pw_start =  new Semaphore(0);
	public static Semaphore Cust_Ready =  new Semaphore(0);
	public static Semaphore NextCustomer = new Semaphore(0);
	public static Semaphore Scales =  new Semaphore(0);
	public static Semaphore Customer_finishes = new Semaphore(0);
	
	public static  int[] cust_Id = new int[55];
	public static int[] postalWorker_Id = new int[3];
	List<Customer> CustomerThreads = new ArrayList<Customer>();
	public static 	List<Integer> CurrentCustomers =  new ArrayList<Integer>();
	public static Map<Integer,Integer> CurrentCustPw = new HashMap<Integer,Integer>();
	public static int custIndex=-1;
	
	
	
	static int CustCount =0;
	boolean alreadyCreated =false;
	//Main method which starts the main thread 
public static void main(String args[])
{
Main po = new Main();
po.start();
}
@Override
public void run()
{
	
	Customer customer;
	PostalWorker pw;
	for(int k=0;k<10;k++)
	Max_Capacity[k] = new Semaphore(0);
	
	//Loop to create new customer threads assign tasks randomly
	for(int i=0;i<50;i++)
	{
		if(i==1 || i%3 ==1)
			customer =  new Customer(task_1,i);
		else if(i==2 || i%3 ==2)
			customer = new Customer(task_2,i);
		else 
		{
			customer = new Customer(task_3,i);
		}
		CustomerThreads.add(customer);
		System.out.println("Customer "+ i +" created");
		if(i<10)
		System.out.println("Customer "+ i +" enters postoffice");
	}
	//Loop to create postal worker threads
	for(int j=0;j<3;j++)
	{
		pw = new PostalWorker(j);
		pw.start();
		Scales.release(1);
	}
	// Loop to start the customer thread based on constraints
	for(int k=0;k<CustomerThreads.size();k++)
	{
	
		if(CustCount<3)
		{
		if(alreadyCreated && k>=10)
		System.out.println("Customer "+ k +" enters postoffice");
		CustomerThreads.get(k).start();
		CustCount++;
		Max_Count.release();
		pw_start.release(1);
		
		}
		else
		{
			try {
				
				NextCustomer.acquire();
				k--;
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			alreadyCreated = true;
		}
	
	}
	
	
	
}
}
