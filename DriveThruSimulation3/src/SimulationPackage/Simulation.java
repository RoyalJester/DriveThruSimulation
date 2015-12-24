package SimulationPackage;
import java.util.Random;

public class Simulation {
	int server1, server2, queue1, queue2, transition;
	int arr_ind, arr2_ind, comp_ind, dep_ind;
	int tempCom, tempSec, tempDep, serv2_ind;
	double sim_tim, totalTime1, totalTime2;
	double newArr, secArr, orderCom, depart, nextServ1, nextServ2, waitTime1, waitTime2;
	double [] arrival_times1;
	double [] arrival_times2;
	double [] serv1_times;
	double [] serv2_times;
	double [] completion;
	double [] departure;
	
	//Class Constructor
	public Simulation(){
		//simulation time
		sim_tim = 0;
		//servers begin idle, queues/transition begin empty
		server1 = 0;
		server2 = 0;
		queue1 = 0;
		queue2 = 0;
		transition = 0;
		//Customer Arrival times to first queue
		arrival_times1 = new double[10000];
		//Customer Arrival times to second queue
		arrival_times2 = new double[10000];
		//Customer service times at first ordering server
		serv1_times = new double[10000];
		//Customer service times at pick up window server
		serv2_times = new double[10000];
		//Customer ordering completion times
		completion = new double[10000];
		//Customer departure times
		departure = new double[10000];
		//Array index of who we are currently working with
		arr_ind = 0;
		arr2_ind = 1;
		comp_ind = 1;
		dep_ind = 1;
		//Values we are currently working with
		newArr = 0;
		secArr = 0;
		orderCom = 0;
		depart = 0;
		//values of service time
		nextServ1 = 0;
		nextServ2 = 0;
		//Responsible for adding the next time to next array slot
		tempCom = 1;
		tempSec = 1;
		tempDep = 1;
		serv2_ind = 0;
		//Wait times for people in line1 and line2
		waitTime1 = 0;
		waitTime2 = 0;
		//total time waititng in line1 and line2 for entire simulation process
		totalTime1 = 0;
		totalTime2 = 0;
	}
	//initialize array elements to "infinity"
	public void init(int customers){
		arrival_times1[customers] = 10000;
		arrival_times2[1] = 10000;
		completion[1] = 10000;
		departure[1] = 10000;
	}
	//Checks to see what is the next event to occur
	public int timing(){
		if(completion[comp_ind]== 0){
			completion[comp_ind] = 10000;
		}
		if(departure[dep_ind]== 0){
			departure[dep_ind] = 10000;
		}
		if(arrival_times2[arr2_ind] == 0){
			arrival_times2[arr2_ind] = 10000;
		}
		
		//sets the values for newArr, secArr, orderCom, depart
		set_values();
		
		//Checks to see what is the next event to happen
		//return 0 if new Arrival is the most imminent 
		if(newArr <= secArr && newArr <= orderCom && newArr <= depart){
			return 0;
		}
		//return 2 if second Arrival is most imminent
		else if (secArr <= newArr && secArr <= orderCom && secArr <=depart){
			return 2;
		}
		//return 1 if order completion is most imminent
		else if (orderCom <= newArr && orderCom <= secArr && orderCom <= depart){
			return 1;
		}
		//return 3 if departure is most imminent
		else if (depart <= newArr && depart <= secArr && depart <= orderCom){
			return 3;
		}
		//return 4 if this program doesnt work, most likely this will never happen
		return 4;
	} 
	
	//Updates the system state, and creates events 
	public int event(int event_type){
		//System.out.println("");
		//System.out.println("Event_type: " +event_type);
		//System.out.println("");
		if(event_type == 0){ //Customer Arrival
			if(server1 == 0){ //If server idle, start ordering
				server1 = 1;
				waitTime1 = 0;
			}
			else{	//otherwise, get in line
				queue1++;
				if(queue1 == 1){ //wait time for person at front of line
					waitTime1 = (completion[arr_ind]-arrival_times1[arr_ind]);
				}
				if(queue1 > 1){ //wait time for a person not at the front of line
					//int num = queue1;
					waitTime1 = 0;
					for(int i = queue1-1; i > 0; i--){
						waitTime1 += serv1_times[arr_ind-i];
					}
				}
				if(waitTime1 < 0){
					waitTime1 = 0;
				}
				else{
					totalTime1 += waitTime1;
				}
			} 
			update();
			log_Arrival();
			sim_tim = newArr;
			arr_ind++;
		}
		else if(event_type == 1){ //Customer Order Completion
			if(queue1 == 0){ //if the queue is empty, no server1 -> idle
				server1 = 0;
			}
			else{ //otherwise server remains busy and person in line advances
				queue1--;
			}
			transition++;
			log_Completion();
			sim_tim = orderCom;
			comp_ind++;
		}
		else if(event_type == 2){ //Customer Enters Second Phase
			if(server2 == 0){
				server2 = 1;
				waitTime2 = 0;
			}
			else{
				queue2++;
				waitTime2 = (departure[dep_ind]-arrival_times2[arr2_ind]);
				
				if(queue2 == 1){ //wait time for person at front of line
					waitTime2 = (departure[dep_ind]-arrival_times2[arr2_ind]);
				}
				if(queue2 > 1){ //wait time for a person not at the front of line
					//int num = queue1;
					waitTime2 = 0;
					for(int i = queue2-1; i > 0; i--){
						waitTime2 += serv2_times[serv2_ind-i];
					}
				}
				
				if(waitTime2 < 0){
					waitTime2 = 0;
				}
				else{
					totalTime2 += waitTime2;
				}
			}
			transition--;
			log_SecondPhase();		
			sim_tim = secArr;
			serv2_ind++;
			arr2_ind++;
			
		}
		else if(event_type == 3){ //Customer Departs
			if(queue2 == 0){
				server2 = 0;
			}
			else{
				queue2--;
			}
			log_Departure();
			sim_tim = depart;
			dep_ind++;
			return 1;

		}
		else if (event_type == 4){ // all values are equal
			//this is a dead path! only to be known to the great programmers who built this code!
			System.out.println("This is the end");
			return 1337;
		}
		return 0;
	}//end event 
	
	//The report is just displaying the current system state
	public void report(){
		System.out.println("--------------------------------------------------------------------------------------------------------------");
		System.out.println("");   
		System.out.println("Ordering Server: " + server1);
		System.out.println("Car Transitioning: " + transition);
		System.out.println("Pick up Server: " + server2);
		System.out.println("Number of people in Line 1: " + queue1);
		System.out.println("Number of people in Line 2: " + queue2);
	}
	//Set some temporary variables so the timing routine can do its job
	public void set_values(){
		newArr = arrival_times1[arr_ind];
		//System.out.println("Arrival: "+newArr);
		
		nextServ1 = serv1_times[arr_ind];
		//System.out.println("First Service: "+nextServ1);
		
		orderCom = completion[comp_ind];
		//System.out.println("Order Complete: "+orderCom);
		
		secArr = arrival_times2[arr2_ind];
		//System.out.println("Second Arrival: "+secArr);
		
		nextServ2 = serv2_times[serv2_ind];
		//System.out.println("Second Service: "+nextServ2);
		
		depart = departure[dep_ind];
		//System.out.println("depart: "+depart);
		
		//System.out.println("");
	}
	
	//When a customer arrives, it will calculate when it will finish ordering,
	// when it will enter the second phase, when it will depart, (Taking into account wait times)
	public void update(){
		completion[tempCom] = arrival_times1[arr_ind]+serv1_times[arr_ind]+waitTime1;
		//System.out.println("Completion just recorded: "+completion[tempCom]);
		
		arrival_times2[tempSec] = completion[tempCom]+.25;
		//System.out.println("Second arrival just recorded: "+arrival_times2[tempSec]);
		
		departure[tempDep] = arrival_times2[tempSec]+serv2_times[arr_ind]+waitTime2;
		//System.out.println("Departure just recoded: "+departure[tempDep]);
		
		//System.out.println("");
		tempCom++;
		tempSec++;
		tempDep++;
	}
	//All "log" functions are simply print out statements
	public void log_Arrival(){
		System.out.println("Customer "+ (arr_ind+1) + " Arrives: "+ newArr+" min");
		System.out.println("With Ordering Service Time: " + nextServ1+" min");
	}	
	public void log_Completion(){
		System.out.println("Customer "+(comp_ind)+" Completed order: "+orderCom+" min");
	}
	public void log_SecondPhase(){
		System.out.println("Customer "+(arr2_ind)+" Entered Second Phase: "+ secArr+" min");
		System.out.println("With Window Service Time: " + nextServ2+" min");
	}
	public void log_Departure(){
		System.out.println("Customer "+(dep_ind)+" Departed: "+ depart+" min");
	}

	//Times customers arrive at the system
	//Time Customer arrives at the system
 	public void arrival_times(float customers){
		Random randomGenerator = new Random();
		double temp = 0;
		for (int i = 0; i<customers; i++){
			int count = 1;
			while(count>0.1){
				double rand = (randomGenerator.nextInt(8));
				if(rand > .1){
					temp += rand;
					arrival_times1[i] = temp+.5;
					System.out.println("Customer Arrival: " + arrival_times1[i]);
					count--;
					}
				}
			}
		System.out.println("");
	}
 	
 	//Times customer spend at ordering server
 	//Time customer spends at ordering server
	public void service_time1(float customers){
		Random randomGenerator = new Random();
	    for(int i =0; i < customers; i++){
	    	int count = 1;
	    	while(count>0){
	    		//Customer will take from 1 to 4 minutes ordering his food
	    		double rand = (randomGenerator.nextInt(4));
	    		if(rand >0.1){
	    			serv1_times[i] = rand;
	    			System.out.println("Customer Service1: " + serv1_times[i]);
	    			count--;
	    		}
	    	}
	    }
	    System.out.println("");
	}
	//Times customers spend at the pick up window
	//Time customer spends at pick up window
	public void service_time2(float customers){
		Random randomGenerator = new Random();
	    for(int i =0; i < customers; i++){
	    	int count = 1;
	    	while(count>0){
	    		//Customer will spend 1-6 paying for his food and waiting for it to be completed
	    		double rand = (randomGenerator.nextInt(6));
	    		if(rand >0.1){
	    			serv2_times[i] = rand;
	    			System.out.println("Customer Service2: " + serv2_times[i]);
	    			count--;
	    		}
	    	}
	    }
	    System.out.println("");
	}
	
}//Class end





