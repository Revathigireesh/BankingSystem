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
import java.sql.ResultSet;

@WebServlet("/getNomini")
public class NominiTable extends HttpServlet {
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
        try {
            response.setContentType("text/html");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank", "root", "#Dhuvihegde26");
            PreparedStatement ps = conn.prepareStatement("select * from Nominees");
            ResultSet rs = ps.executeQuery();
            PrintWriter out = response.getWriter();

            // Include Bootstrap links
            out.println("<html><head>");
            out.println("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css\">");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js\"></script>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js\"></script>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js\"></script>");
            out.println("</head><body style=\"background-color: #2c3e50\">");

            // Center the table
            out.println("<div class=\"container\">");

            // Additional div for the table background
           out.println("<div style=\"background-color:white;\">");

            // Table header
            out.println("<table class=\"table table-bordered table-striped\">");
            out.println("<thead style=\"background-color:#488A99\">");
            out.println("<tr><th>Nominees Name</th><th>Account No</th></tr>");
            out.println("</thead><tbody>");

            // Table data 
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString(1) + "</td>");
                out.println("<td>" + rs.getString(2) + "</td>");
                out.println("</tr>");
            }

            out.println("</tbody></table></div></div></body></html>");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

