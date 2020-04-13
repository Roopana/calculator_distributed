# Calculator App
Simple Calculator using Java Servlet with shared access to calculation history of clients. The max records to display is capped to 10.

# Concepts Used
  * TCP Sockets for communication between client and server
  
* Synchronized threads to access calculation history of all clients
  
* Java Servlet to build the UI (Running on TomCat Server)


# Concepts Used
 * TCP Sockets for communication between client and server
  
* Synchronized threads to access calculation history of all clients
  
* Java Servlet to build the UI (Running on TomCat Server)

# Execution Steps
* Bring up the server by executing [CalcServer](https://github.com/Roopana/calculator_distributed_server/blob/master/src/sharedCalculator/CalcServer.java) file.
* Run [webapp project](https://github.com/Roopana/calculator_client_webapp/tree/master/sharedCalculatorWebApp) on Tomcat. This display the GUI as shown in below screenshot.
* To perform calculations, access the GUI through URL: http://localhost:8080/sharedCalculatorWebApp/index.jsp

# Screenshot of the UI

![](https://github.com/Roopana/calculator_client_webapp/blob/master/Calc_WebApp.png)

# Decsription

A web app (in java) to log calculations as they happen and shares those calculations with everyone connected to the website.

For example, user A and user B go to your site at the same time. User A calculates "5 + 5", which equals "10". This is logged below the calculator as "5 + 5 = 10". User B is updated about this calculation right after user A posts it. Now, user B calculates "3 x 4".This calculates to “12” and displays "3 x 4=12" right below the prior calculation. User A sees this update immediately after user B posts it.

Results remain between sessions. Only last 10 calculations descending from most recent to oldest are displayed the history

# Limitations of the app:
* The calculator can perform only +,-,x,/ operations
* It can take values only in the range of double data type

