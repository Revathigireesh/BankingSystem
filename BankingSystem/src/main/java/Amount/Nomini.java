package Amount;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FetchData")
public class Nomini extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the ID parameter from the request
        String id = request.getParameter("id");

        // Validate if ID is provided
        if (id == null || id.isEmpty()) {
            out.println("<div style='background-color: #f8d7da; padding: 15px; text-align: center;'>Please provide an ID.</div>");
            return;
        }
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank", "root",
                    "#Dhuvihegde26");

            // Prepare the SQL query to fetch data for the given ID
            String query = "SELECT u.id, u.name, u.accc_no, u.acco_balance, n.nominee_name " + "FROM user u LEFT JOIN Nominees n ON u.accc_no = n.accc_no "
                    + "WHERE u.id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Display the retrieved data
            out.println("<html>");
            out.println("<head><style>body { background-color: #f4f4f4; font-family: Arial, sans-serif; .container { width: 80%; margin: 0 auto; }}</style></head>");
            out.println("<body style=\"background-color:#2c3e50\">");
            out.println("<div style='display: flex; justify-content: center; align-items: center; height: 100vh;'>");
            out.println("<div style='background-color: #fff; padding: 50px; border-radius: 10px; box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);'>");

            out.println("<h2 style='color: #333; text-align: center;'>User Details</h2>");
            if (rs.next()) {
                out.println("<p><strong>ID:</strong> " + rs.getString("id") + "</p>");
                out.println("<p><strong>Name:</strong> " + rs.getString("name") + "</p>");
                out.println("<p><strong>Account No:</strong> " + rs.getString("accc_no") + "</p>");
                out.println("<p><strong>Balance:</strong> " + rs.getString("acco_balance") + "</p>");
                // Check if nominee name is not null
                if (rs.getString("nominee_name") != null) {
                    out.println("<p><strong>Nominee Name:</strong> " + rs.getString("nominee_name") + "</p>");
                } else {
                    out.println("<p>No nominee found for this user.</p>");
                }
            } else {
                out.println("<p>No user found with ID: " + id + "</p>");
            }
            out.println("</div>"); // Close container
            out.println("</div>"); // Close flex container
            out.println("</body></html>");

            // Close resources
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            out.println("<div style='background-color: #f8d7da; padding: 15px; text-align: center;'>Error: " + e.getMessage() + "</div>");
            e.printStackTrace(out);
        }
    }
}
