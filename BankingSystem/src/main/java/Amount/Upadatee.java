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

@WebServlet("/UpadateData")
public class Upadatee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the ID and updated name from the request
        String id = request.getParameter("id");
        String newName = request.getParameter("name");

        // Validate if ID and new name are provided
        if (id == null || id.isEmpty() || newName == null || newName.isEmpty()) {
            out.println("Please provide both ID and a new name.");
            return;
        }

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank", "root", "#Dhuvihegde26");

            // Prepare the SQL query to update the user's name based on the ID
            String query = "UPDATE user SET name = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newName);
            ps.setString(2, id);

            // Execute the update query
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                out.println("<h2>User data updated successfully.</h2>");
            } else {
                out.println("<h2>Failed to update user data.</h2>");
            }

            // Close resources
            ps.close();
            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
            e.printStackTrace(out);
        }
    }
}