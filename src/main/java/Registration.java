import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/register")
public class Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Registration() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String attendeesno = request.getParameter("attendeesno");

        System.out.println("Servlet doGet() invoked");
        System.out.println("Received data - Name: " + name + ", Phone: " + phone + ", Email: " + email + ", Attendees: " + attendeesno);

        Connection con = null;
        Statement stmt = null;
        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/realdb", "root", "krishna@30");
            stmt = con.createStatement();

            // Insert data into the 'event' table
            String query = "INSERT INTO event (name, phone, email, attendeesno) VALUES ('" + name + "', '" + phone + "', '" + email + "', '" + attendeesno + "')";
            int result = stmt.executeUpdate(query);

            // Send feedback to the user
            out.println("<html><body bgcolor='wheat'><center><h1>");
            if (result != 0) {
                out.println(name + ", you are successfully registered.");
            } else {
                out.println(name + ", registration failed. Please try again.");
            }
            out.println("</h1></center></body></html>");

        } catch (ClassNotFoundException e) {
            out.println("Error loading database driver: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}