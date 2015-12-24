Names:  Jesus Gonzalez

Computer Simulation – Drive-Thru Simulation
File: README

Files included:  README; Start.java; Simulation.java, driver.txt

Compile instructions:
1.	Compile and run the program in java eclipse IDE or similar

Operating instructions:
1.	Open Java eclipse IDE
2.	Select your workspace folder or create a new one
3.	Select File ? New ? Java Project
4.	Provide a Project name. i.e. DriveThruSimulation
5.	In the package explorer window, right click on the Project name ? New ? Package
6.	Provide a name for the Package. i.e. DriveThruPackage
7.	Open your workspace folder (i.e.  C:\Users\Documents
8.	Open the Project folder (i.e. DriveThruSimulation)
9.	Save the driver.txt file is this folder
10.	Then, open the src folder
11.	Open the package folder (i.e. DriveThruPackage)
12.	Save the driver.java and driveThru.java files in this folder
13.	Close and reopen the Eclipse, and then you should be able to see driver.java and driveThru.java in the Project folder on the Package explorer window
14.	Click to open both files
15.	Run the driveThru.java by clicking the green run button   on the toolbar

Purpose:
Simulation takes arrival rates, ordering rates and pick up rates, and tries to computer the average time a customer spends waiting in line before ordering or picking up his/her food.

Since we are using a normal distribution, we assume it takes between 1 and 8 minutes for a new custoer to arrive, as well we assume it takes a customer 1-4 minutes to order his/her food, and finally we assume it takes a customer 1-6 minutes to pay and wait for his food to be done.

Design Description:

A computer simulation model was developed for the Drive-Thru of a fast food restaurant.  Our simulation project is based on stochastic simulation because we created a projection which is based on a set of random values.  The performance measures such as the number of events, the average time each event spends in each window (servers), and the average waiting time for the events, and the average time of completion.  

This operation is served by one ordering server and one window (pick-up) server. Customers are assigned with arrival time and service times for first window and second window at the beginning of the program.  The customer must be served in the first window before proceeding to the second window.  If the server, where the customer is going, is busy, the customer must wait in the queue before going to server.


Deficiencies and Bugs:

1. Our distribution is just a normal distribution
2. We don't account for the time of day (in the regard that there should be different rates at night then in the day time)

Extra features/algorithms/functionality:

• none, apart from extra functions whose purpose is only to print results and states
