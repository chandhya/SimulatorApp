/**@author Chandhya
 * Simulator Class that initiates the two processes - CPU and memory
 *  
 * **/
package computerSystemSimulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulator {
	
	public static List<String> Instructions = new ArrayList<String>();
	public static String programFile;  // program file String can be altered to test Program02
	//final static Charset ENCODING = StandardCharsets.UTF_8;
	static boolean programEnd = false;
	static String[] instrSet;
	static String s; 
	static int I;
	//static Path programFilePath = Paths.get(programFile);
	static String from;
	int address =0;
	String i ;
	InputStream in ;
	String printData;
	String instrs;
	List<String> instrSetList;
	String line;
	
	//Main method to invoke the start of two processes
	public static void main(String args[])
	{
		
		Simulator sm = new Simulator();
		sm.startProcess();
		System.out.println("Execution Complete");
		
	}
	//Method to start the process using process builder
	public void startProcess() {
		
		try {
			if(programFile== null )
				programFile = "Program02.txt";
			 BufferedReader br = new BufferedReader(new FileReader(programFile));
			 String strLine;
			 while((strLine = br.readLine())!=null)
		        {
		        	
				 Instructions.add(strLine);
		        	
		        }
			ProcessBuilder Memoryprocessbuilder = new ProcessBuilder("java.exe","-cp","bin","computerSystemSimulation.Memory");
			Process Memory =  Memoryprocessbuilder.start();
			instrSetList = new ArrayList<String>();
			BufferedReader inp = new BufferedReader(new InputStreamReader(Memory.getInputStream()));
			for(int i=0;i<Instructions.size();i++)
			{
			instrSetList.add(i, inp.readLine().toString());
			}
			
			Memory.destroy();
			ProcessBuilder CPUprocessbuilder = new ProcessBuilder("java.exe","-cp","bin","computerSystemSimulation.CPU");
			Process CPUP =  CPUprocessbuilder.start();
			//System.out.println("cpu starts");
			CPU.executeProgram();
			BufferedReader inC = new BufferedReader( new InputStreamReader(CPUP.getInputStream()) );
			do
			{
			printData = inC.readLine();
			
			BufferedWriter opC = new BufferedWriter( new OutputStreamWriter(CPUP.getOutputStream()));
			if(I!=0)
			opC.write(I);
			//System.out.println("Input got from user is "+I);*/
			}while(programEnd==false);
			CPUP.destroy();
			} catch (IOException e) {
			
			e.printStackTrace();
		}
			
	}
	public static Object readinput(int portNo) {
		
		Scanner inp = new Scanner(System.in);
		if(portNo == 1)
		{
		 System.out.println("Enter a character");
	      s = inp.nextLine();
	      inp.close();
	      return (Object)s;
		}
		else if(portNo == 2)
	     {
	      System.out.println("Enter an integer only");
	      I = inp.nextInt();
	      return (Object)I;
	      	      
	     }
		inp.close();
		return 0;
			
	}
	public static void writeOutput(int AC,boolean flag)
	{
		if(programFile.equals("Program01.txt"))
		System.out.println("Sum of the numbers entered is: "+AC);
		else if(programFile.equals("Program02.txt"))
		System.out.println("Reversed order is"+AC);
		else 
			System.out.println("Output is"+AC);
	}

}
