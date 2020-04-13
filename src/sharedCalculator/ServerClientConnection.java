package sharedCalculator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * Thread for connection between server and each client
 * @author vcroopana
 *
 */
public class ServerClientConnection extends CalcServer implements Runnable{

	Socket clientSocket;
	double inp1 = 0;
	double inp2 = 0;
	String op = null;
	private static String INPUT1_PREFIX = "INPUT1;";
	private static String INPUT2_PREFIX = "INPUT2;";
	private static String OP_PREFIX = "OP;";
	private static String HISTORY_PREFIX = "HIST;";

	
	public ServerClientConnection(Socket socket) {
		this.clientSocket = socket;
	}

	@Override
	public void run() {
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			if (clientSocket != null) {
				dis = new DataInputStream(clientSocket.getInputStream());
				dos = new DataOutputStream(clientSocket.getOutputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) { // keep listening to server and update calc history
			System.out.println("Waiting for input from client");

			try {
				while (dis.available() < 1) {
					Thread.sleep(200); // sleep for 200 ms if no input from server
				}

				String inputStr = dis.readUTF();
				System.out.println("Recieved input at server: " + inputStr);
				
				//If input is the operand 1
				if (inputStr.startsWith(INPUT1_PREFIX)) {

					inp1 = Double.valueOf(CalculatorUtil.getInput(inputStr));

				}
				// If input is the operator
				else if (inputStr.startsWith(OP_PREFIX)) {

					op = CalculatorUtil.getInput(inputStr);

				} 
				// If input is the operand 2
				else if (inputStr.startsWith(INPUT2_PREFIX)) {

					inp2 = Double.valueOf(CalculatorUtil.getInput(inputStr));

					try {
						double output = CalculatorUtil.evaluate(inp1, op, inp2);
						String outputString = CalculatorUtil.constructCalcString(inp1, op, inp2, output);

						System.out.println("Writing curr op str :" + outputString);
						dos.writeUTF(String.valueOf(output));

						System.out.println("Writing History :" + HISTORY_PREFIX + calcHistory.toString());
						dos.writeUTF(calcHistory.toString());

						updateHistory(outputString);

					} catch (ArithmeticException ae) {

						System.out.println("Exception caught:" + ae.getMessage());
						dos.writeUTF("ERROR:" + ae.getLocalizedMessage());
					}
				}

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void updateHistory(String calculation) {

		if (calcHistory.size() == 10) {
			calcHistory.remove(); // removes head of the queue if current size is 10
			calcHistory.add(calculation); // inserts in the end of the queue
		} else {
			calcHistory.add(calculation); // inserts in the end of the queue
		}

	}

}
