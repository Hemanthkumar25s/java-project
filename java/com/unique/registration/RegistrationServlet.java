package com.unique.registration;

import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String e_mail = request.getParameter("email");
		String password = request.getParameter("pass");
		String mobile = request.getParameter("contact");
		
		RequestDispatcher dispatcher = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");   
			String url = "jdbc:mysql://localhost:3306/SIGNUP?useSSL=false";   
	        String userName = "root";
	        String pass = "Hemanth->25012005";
	        
	        // Creating the connection
			con = DriverManager.getConnection(url, userName, pass);
			
			// Creating the statement
			String sql = "INSERT INTO USERS (name, e_mail, password, mobile) VALUES(?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, name);
			ps.setString(2,  e_mail);
			ps.setString(3,  password);
			ps.setString(4,  mobile);
			
			int rowCount = ps.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if (rowCount > 0)
			{
				request.setAttribute("status", "success");
			}
			else
			{
				request.setAttribute("status", "failed");
			}
			
			dispatcher.forward(request, response);
			
		}
		catch (SQLIntegrityConstraintViolationException e) {
        // Handle duplicate entry for e_mail
			request.setAttribute("status", "duplicate");
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("status", "error");
		}
		
		finally {
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
	}
}
