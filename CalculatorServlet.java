
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CalculatorServlet
 */
@WebServlet("/calculatorservlet")
public class CalculatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CalculatorServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		double inp1 = 0;
		String op = "ADD"; // default value when form is loaded first time
		double inp2 = 0;

		try {
			inp1 = Double.valueOf(request.getParameter("input1"));
			if(request.getParameter("operator")!=null) {
				op = request.getParameter("operator");
			}
			
			System.out.println("op:"+ op);
			inp2 = Double.valueOf(request.getParameter("input2"));
			String[] outputs = CalClient.executeCall(inp1, op, inp2);

			HttpSession session = request.getSession(true);
			
			session.setAttribute("history", getConcatHist(outputs[1]));
			session.setAttribute("input1", inp1);
			session.setAttribute("operator", op);
			session.setAttribute("input2", inp2);
			session.setAttribute("result", outputs[0]);

		} catch (NumberFormatException e) {
			HttpSession session = request.getSession(true);
			session.setAttribute("result", "Invalid input");
			//session.setAttribute("history", "Invalid input");

		}
		// send teh response back to the same page
		response.sendRedirect("index.jsp");
	}
 
	/**
  *  Format calculation history to be displayed
  * @param csvHist
  * @return
  */
	private String getConcatHist(String csvHist) {
		String trimmed = csvHist.substring(1, csvHist.length() - 1);
		String histconcat = "";
		if (trimmed.length() > 0) {

			String hist[] = trimmed.split(",");

			for (int i = 0; i < hist.length; i++) {
				histconcat = histconcat + hist[hist.length - 1 - i] + "<br>";
			}
		} else {
			histconcat = "No Calculation history found";
		}
		return histconcat;
	}
}
