package sharedCalculator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * Starts calculations ervere on port 5056
 * @author vcroopana
 *
 */
public class CalcServer {
	private static int serverPort;
	private static ServerSocket serverSocket;
	
	private static ConcurrentHashMap<String, Socket> serverClientSocketMap = new ConcurrentHashMap<String, Socket>();
	
	//calculation history shared by all client connections 
	//using thread safe collection: Blocking Queue since this list is accessed by client threads
	public static BlockingQueue<String> calcHistory = new LinkedBlockingQueue<String>();
	
	public static void main(String[] args) {
		
		serverPort = 5056;
		startServer(serverPort);
		
	}
	
	public static void startServer(int serverPort2) {
		try {
			serverSocket = new ServerSocket(serverPort2);
			
			System.out.println("Starting server on the port: " + serverPort2);
			
			while (true) { // loop to accept multiple client connections for a single server
				Socket socket = serverSocket.accept();
				System.out.println("Accepted client socket:"+ socket.getInetAddress().toString()+ "on port:"+ socket.getPort());
				
				serverClientSocketMap.put(socket.getInetAddress().toString()+socket.getPort() , socket);
				ServerClientConnection serverClientConnection = new ServerClientConnection(socket);
				new Thread(serverClientConnection).start();
			}

		} catch (IOException e) {
			System.out.println("Exception in starting server");
			e.printStackTrace();
		}
	}

}
