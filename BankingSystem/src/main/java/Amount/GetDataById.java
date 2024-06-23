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

@WebServlet("/EditUserData")
public class GetDataById extends HttpServlet {
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
            out.println("Please provide an ID.");
            return;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank", "root", "#Dhuvihegde26");

            // Prepare the SQL query to fetch data for the given ID
            String query = "SELECT * FROM user WHERE id = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, id);

            // Execute the query
            rs = ps.executeQuery();

            // Display the retrieved data for editing
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Edit User Data</title>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js\"></script>\r\n"
            		);
            // Include Bootstrap CSS
            out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
            out.println("<style>");
            // Custom CSS for centering the form container
            out.println(".form-container {");
            out.println("    margin-top: 100px;"); // Adjust margin top to center vertically
            out.println("    padding: 20px;");
            out.println("    border: 1px solid #ced4da;");
            out.println("    border-radius: 8px;");
            out.println("    background-color: #fff;");
            out.println("    box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);");
            out.println("}");
       
            
            out.println("</style>");
            out.println("</head>");
            out.println("<body style='background-color:#2c3e50;'>");
            // Bootstrap container class
            out.println("<div class=\"container\">");
            // Bootstrap row class with centered content
            out.println("<div class=\"row justify-content-center\">");
            // Bootstrap column class with size lg-6
            out.println("<div class=\"col-lg-6\">");
            // Custom class for styling
            out.println("<div class=\"form-container\">");
            out.println("<h2 class=\"mb-4\">Edit User Data</h2>");
            if (rs.next()) {
                out.println("ID: " + rs.getString("id") + "<br>");
                out.println("<form action='UpadateData' method='post'>");
                // Bootstrap form group
                out.println("<div class=\"form-group\">");
                out.println("Name: <input type='text' name='name' value='" + rs.getString("name") + "'><br>");
                out.println("</div>");
                out.println("<input type='hidden' name='id' value='" + rs.getString("id") + "'>");
                // Bootstrap primary button
                out.println("<button type='submit' class='btn btn-primary'style='margin-top: 10px;'>Update</button>");
                out.println("</form>");
            } else {
                out.println("No user found with ID: " + id);
            }
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
            e.printStackTrace(out);
        } finally {
            // Close resources in the finally block to ensure they are closed even if an exception occurs
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                out.println("Error closing resources: " + ex.getMessage());
                ex.printStackTrace(out);
            }
        }
    }
}
