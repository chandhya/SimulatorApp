package semaphoreUsage;
public class PostalWorker extends Main
{
	int custId;
	int pId;
	boolean pwAlreadyCreated =false;
public PostalWorker(int  pId) 
{
	this.pId = pId;
	Main.postalWorker_Id[pId] = pId;
}
//Postal worker thread that runs in an infinite loop to serve customers in order
@Override	
public void run()
{
	while(true)
	{
		try {
		
			pw_start.acquire();
			if(!pwAlreadyCreated)
			System.out.println("Postal worker "+ pId +" created");
			pwAlreadyCreated = true;
			Main.custIndex++;
			System.out.println("Postal Worker "+pId+" serving Customer "+Main.cust_Id[Main.custIndex]);
			CurrentCustPw.put(Main.cust_Id[Main.custIndex],this.pId);
			Thread.sleep(2000);
			PostalWorkerReady.release(4);
			Cust_Ready.acquire();
			Postal_Worker.release();
			Customer_finishes.acquire();
			NextInQueue.release(); // Semaphore to indicate next customer in queue is can be served now
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}
}
}
