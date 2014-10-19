package computerSystemSimulation;
/*@author Chandhya
 * 
 * Memory file that holds address and values corresponding that address location
 * */
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;




public class Memory extends Simulator {
	
	static int instr[] = new int[1000];;
	static char[] instrc;
	
	static int adr=0;
	static BufferedReader memoryIn;
	 
	Memory(){
		write();
	}
	//Method to load instructions into the memory
	public  void write()
	{
		try {
			if(programFile== null )
				programFile = "Program02.txt";
			 BufferedReader br = new BufferedReader(new FileReader(programFile));
			 String strLine;
			 while((strLine = br.readLine())!=null)
		        {
		        	
				 Instructions.add(strLine);
		        	
		        }
			//Instructions = Files.readAllLines(programFilePath, ENCODING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(adr=0;adr<Instructions.size();adr++)
		{

			instr[adr] = Integer.parseInt(Instructions.get(adr));  
		}
		for(int k=0 ;k<instr.length;k++)
		{
			System.out.println(instr[k]);
		}	
		//System.out.println(instr);
		instr.toString();
				
	}
	public static  void main(String args[]) {
	
		Memory m = new Memory();
		m.write();
		System.out.println(instr);

			
	}

	
}
