
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Allowed Operations: ADD, SUBTRACT, MULTIPLY, DIVIDE Allowed range of
 * Integers: Inputs and output should be within double data type limit
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


	public static String[] executeCall(double inp1, String operand, double inp2) {
		Socket socket;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		String[] outputs = new String[2];
		try {
			System.out.println("Call executed");
			// establish connection with server
			InetAddress clientIP = InetAddress.getByName(clientIPStr);
			socket = new Socket(clientIP, serverPort);

			System.out.println(socket);

			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			dos.writeUTF(INPUT1_PREFIX + inp1);

			dos.writeUTF(OP_PREFIX + operand); 

			dos.writeUTF(INPUT2_PREFIX + inp2);
			
			outputs[0] = readOutput(dis);
			outputs[1] = readOutput(dis);
			
			System.out.println("Output from sever:" + outputs[0]);
			System.out.println("History from sever:" + outputs[1]);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputs;
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
