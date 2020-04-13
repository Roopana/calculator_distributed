package sharedCalculator;

import java.util.HashMap;
import java.util.Map;

public class CalculatorUtil {

	@SuppressWarnings("serial")
	private static Map<String, String> operatorMap = new HashMap<String, String>() {
		{
			put("ADD", "+");
			put("SUBTRACT", "-");
			put("MULTIPLY", "x");
			put("DIVIDE", "/");
		}
	};

	public static String constructCalcString(double inp1, String op, double inp2, double output) {
		
		if(output == -0.0) {
			output = 0.0;
		}
		return inp1 + " " + operatorMap.get(op) + " " + inp2 + " = " + output;
	}

	public static double evaluate(double inp1, String op, double inp2) {
		double res = 0;

		switch (op) {
		case "ADD":
			res = inp1 + inp2;
			break;
		case "SUBTRACT":
			res = inp1 - inp2;
			break;
		case "MULTIPLY":
			res = inp1 * inp2;
			break;

		case "DIVIDE":
			res = inp1 / inp2;
			break;
		default:
			System.out.println("Invalid Operation");
			break;
		}
		System.out.println("Evaluated result as:" + res);
		return res;
	}

	public static String getInput(String inputStr) {
		if (inputStr.contains(";")) {
			String[] temp = inputStr.split(";");
			return temp[1];
		}
		return "0";

	}

	public boolean isValidDouble() {
		return false;

	}

}
