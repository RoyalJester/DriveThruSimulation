package SimulationPackage;

import java.util.*;
import java.io.*;

public class Start {
	public static void main(String arg[]) throws IOException{
		
		//READ FILE, STORE INPUT IN ARRAY/////////////////////
		Scanner inFile1 = new Scanner(new File("driver.txt"));
	    Float[] f_array = new Float[100];
	    int f_count = 0;
	    while (inFile1.hasNext()) {
	      // find next line
	      float token1 = inFile1.nextFloat();
	      f_array[f_count++] = token1;
	    }
	    inFile1.close();
	    //END OF FILE READING AND STORING/////////////////////
	    
	    float customers = f_array[0]; //Holds Number of Customers that will appear this day
	    float customersSaved = f_array[0]; 
		Simulation obj = new Simulation();
		
		//Creating arrival times and service times
		obj.arrival_times(customers);
		obj.service_time1(customers);
		obj.service_time2(customers);
		
		obj.init((int) customers);
		obj.report();
		
		while(customers>0){
			System.out.println("Simulation Time is: "+ obj.sim_tim);
			System.out.println("");
			int countEveryone = obj.event(obj.timing());
			System.out.println("");
			obj.report();
			//only decrease the loop counter when someone departs
			customers -= countEveryone;		
		}//while loop
		
		
		System.out.println("");
		System.out.println("----RESULTS!---------------------------");
		System.out.println("Simulation Time: "+ obj.sim_tim);
		System.out.println("Number of Customers Today: "+customersSaved);
		System.out.println("Total wait time in line 1: "+obj.totalTime1+" min");
		System.out.println("Total wait time in line 2: "+obj.totalTime2+" min");
		System.out.println("Average expected waiting time: "+((obj.totalTime1+obj.totalTime2)/(customersSaved))+" min");
		System.out.println("");
		System.out.println("---------------------------------------");
		
	}//main function
}
