/*@author Chandhya
 * CPU executes all the instructions in a sequence
 * 
 * 
 * */


package computerSystemSimulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Stack;

public  class CPU extends Simulator{
	
	static int address = 0;
	 static int PC=0;
	 static int SP=0;
	 static int IR=0;
	 static int AC=0;
	 static int X=0;
	 static int Y=0;
	 
	 static int var1;
	static Stack<String> st = new Stack<String>();;
	//Instructions = Files.readAllLines(programFilePath, ENCODING);
	CPU(){
		executeProgram();
		
	}
	//Actual Program execution to execute instructions begins here
	public static void executeProgram() {
		try {
			if(programFile== null)
				programFile ="Program02.txt";
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
		System.out.println(Instructions);
		Object obj = null;
		
		for(int PC=0 ; PC<Instructions.size();PC++)
		{
			if(programEnd)
				break;
			IR=Integer.parseInt(Instructions.get(PC));  //Loading Instructions in instruction register
			//System.out.println("CPU :Executing instruction "+Instructions.get(PC));
			switch(IR)    //Switch case to handle each instruction in the program
			{
			case 0 :
				programEnd = true;
				break;
			case 1:
				AC = var1;
				AC= Integer.parseInt(obj.toString());
			
				break;
			case 2:
				AC= Integer.parseInt(instrSet[PC]);
				break;
			case 3:
				instrSet[PC] = String.valueOf(AC);
				break;
			case 4:
				AC = AC+X;
				break;
			case 5:
				AC = AC+Y;
				break;
			case 6:
				AC = AC -X;
				break;
			case 7:
				AC = AC -Y;
				break;
			case 8:
				obj = readinput(2);
				AC= Integer.parseInt(obj.toString());
				//System.out.println(AC);
				break;
			case 9:
				writeOutput(AC,false);
				break;
			case 10:
				X = AC;
				break;
			case 11:
				Y = AC;
				break;
			case 12:
				AC = X;
				break;
			case 13:
				AC = Y;
				break;
			case 14:
				PC++;
				break;
			case 15:
				if(AC==0)
				jumpToAdr(PC);
				break;
			case 16:
				if(AC!=0)
				jumpToAdr(PC);
				break;
			case 17:
				st.push(String.valueOf(PC));
				jumpToAdr(PC);
				break;
			case 18:
				st.pop();
				jumpToAdr(PC);
				break;
			case 19:
				X++;
				break;
			case 20:
				X--;
				break;
			case 30:
				programEnd = true;
				break;
			default:
				String str = String.valueOf(IR);
				String s1 = Character.toString(str.charAt(0));
				String s2 = Character.toString(str.charAt(1));
				int i = Integer.parseInt(s1);
				int j = Integer.parseInt(s2);
				if(i == 7)
				{
				Y = j;
				AC= AC-Y;
				}
				break;
			}
			
			//System.out.println("NEXT "+Instructions.get(PC++));
		}
		
	}

	private static void jumpToAdr(int pC2) {
		PC++;		
	}

	public static void main(String args[]) {
		
	    executeProgram();
	     
	}
}
