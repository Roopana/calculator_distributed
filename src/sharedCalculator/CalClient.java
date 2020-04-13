package sharedCalculator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Allowed Operations: ADD, SUBTRACT, MULTIPLY, DIVIDE
 * Allowed range of Integers: Inputs and output should be within double data type limit
 * 
 * @author vc roopana
 *
 */

public class CalClient {

	static int serverPort = 5056;
	static String clientIPStr = "localhost";
	private static String INPUT1_PREFIX = "INPUT1;";
	private static String INPUT2_PREFIX = "INPUT2;";
	private static String OP_PREFIX = "OP;";
	

	public static void main(String args[]) {
		executeCall();
		
	}
	
	public static void executeCall() {
		Socket socket;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		try {
			// establish connection with server
			InetAddress clientIP = InetAddress.getByName(clientIPStr);
			socket = new Socket(clientIP, serverPort);

			System.out.println(socket);

			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			boolean exit = false;
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			do {
				// request operation from server
				System.out.println("Enter the first input");
				String inp1 = scanner.next();
				if (inp1.equalsIgnoreCase("EXIT")) {
					exit = true;
				} else {
					dos.writeUTF(INPUT1_PREFIX+ Double.valueOf(inp1));

					System.out.println("Enter the operation, ADD, SUBTRACT, MULTIPLY, DIVIDE");
					dos.writeUTF(OP_PREFIX  + scanner.next()); // TODO : What kind of calcs are posisble?

					System.out.println("Enter the second input");
					dos.writeUTF(INPUT2_PREFIX + scanner.nextDouble());

					System.out.println("Output from sever:" + readOutput(dis));
					System.out.println("History from sever:" + readOutput(dis));
				}

			} while (!exit);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String readOutput(DataInputStream dis) {
		try {
			while (dis.available() < 1) {
				Thread.sleep(200);
			}
			return dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
