package semaphoreUsage;

public class Customer extends Main{

	private String Task;
	private int custId;
	public Customer(String task,int custId)
	{
		this.Task =  task;
		this.custId = custId;
		Main.cust_Id[custId] = custId;
	}
	//Customer thread that starts to perform required operations
	@Override
	public void run()
	{
		
		try {
			Max_Count.acquire();
			Cust_Ready.release();
			PostalWorkerReady.acquire();
			if(Main.CurrentCustPw.containsKey(this.custId))
			{
				Postal_Worker.acquire();
			Thread.sleep(1000);
			}
			
			else
				NextInQueue.acquire();
			
			PostalWorkerReady.acquire();
			customer_action(Task,custId,"initiate");
			/*
			Customer_waitsfor_Postalworker.acquire();
			customer_action(Task,custId,"complete");
			
			Postal_Worker.release();
			*/
		
		} catch (InterruptedException e) 
		{
			
			e.printStackTrace();
		}
		
		
	}
	//Method to print the customer actions to be performed based on task assigned to each customer
	private void customer_action(String task, int custId,String type) {
		
			try {
				if(task.equals(task_1))
				{
					if(type.equals("initiate"))
					{
					System.out.println("Customer "+this.custId+" asks postal worker"+CurrentCustPw.get(this.custId)+" to buy stamps ");
					System.out.println("Postal worker "+CurrentCustPw.get(this.custId)+"finished serving Customer "+custId);
					Customer_finishes.release();
					Thread.sleep(6000);
					
					System.out.println("Customer "+custId+" finishes buying stamps ");
					System.out.println("Customer "+custId+" leaves postoffice ");
					System.out.println("Joined Customer "+custId);
					Main.CustCount--;
					NextCustomer.release();
					}
				}
				else if(task.equals(task_2))
				{
					if(type.equals("initiate"))
					{
					System.out.println("Customer "+custId+" asks postal worker "+CurrentCustPw.get(this.custId)+" to mail a letter ");
					System.out.println("Postal worker "+CurrentCustPw.get(this.custId)+"finished serving Customer "+custId);
					Customer_finishes.release();
					Thread.sleep(9000);
					System.out.println("Customer "+custId+" finishes mailing a letter");
					System.out.println("Customer "+custId+" leaves postoffice ");
					System.out.println("Joined Customer "+custId);
					Main.CustCount--;
					NextCustomer.release();
					}
					
				}
				else if(task.equals(task_3))
				{
					if(type.equals("initiate"))
					{
					Thread.sleep(1000);
					Scales.acquire();
					System.out.println("Customer "+custId+" asks postal worker "+CurrentCustPw.get(this.custId)+" to mail a package");
					System.out.println("Scales in use by Postal worker" +CurrentCustPw.get(this.custId));
					Scales.release();
					System.out.println("Scales released by postal worker"+CurrentCustPw.get(this.custId));
					System.out.println("Postal worker "+CurrentCustPw.get(this.custId)+" finished serving Customer "+custId);
					Customer_finishes.release();
					Thread.sleep(12000);
					System.out.println("Customer "+custId+" finishes mailing a package");
					System.out.println("Customer "+custId+" leaves postoffice ");
					System.out.println("Joined Customer "+custId);
					Main.CustCount--;
					NextCustomer.release();
					
					}
				}
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}


