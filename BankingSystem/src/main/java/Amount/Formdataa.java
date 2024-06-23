package Amount;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Servlet implementation class Dora
 */
@WebServlet("/editdata")
public class Formdataa extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String INSERT_QUERY = "INSERT INTO user(ID,NAME,ACCC_NO, ACCO_BALANCE) VALUES(?, ?, ?, ?)";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Formdataa() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		   response.setContentType("text/html");
		     PrintWriter pw = response.getWriter();
		     String id = request.getParameter("id");
		     String name = request.getParameter("name"); 
		     String accNo = request.getParameter("accNo");
		     String balance = request.getParameter("balance");
		     if (name == null || name.trim().isEmpty()) {
		    	    pw.println("Error: Name cannot be empty");
		    	    return;
		    	}
		     try {
		         Class.forName("com.mysql.cj.jdbc.Driver");
		         try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank", "root", "#Dhuvihegde26");
		              PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {
		             ps.setString(1, id);
		             ps.setString(2, name);
		             ps.setString(3, accNo);
		             ps.setString(4, balance);

		             int count = ps.executeUpdate();
                    //printWriter rs=ps.executeQuery();
		             if (count == 0) {
		                 pw.println("<body><h2>Record not stored into the database</h2>");
		             } else {
		                 pw.println("<h2>Data stored Successfully</h2>");
		              pw.println("<h5>ID:"+id+"</h5>");
		                 pw.println("<h5>Name:"+name+"</h5>");
		                 pw.println("<h5>Acc_No:"+accNo+"</h5>");
		                 pw.println("<h5>Balance:"+balance+"</h5></body>");
		             }
		         }
		     } catch (ClassNotFoundException | SQLException e) {
		         pw.println("Error: " + e.getMessage());
		         e.printStackTrace();
		     } catch (Exception e) {
		         pw.println("Error: " + e.getMessage());
		         e.printStackTrace();
		     } finally {
		         pw.close();
		     }
		 }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
