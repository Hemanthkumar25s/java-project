package com.unique.registration;

import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.DriverManager;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String e_mail = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/SIGNUP?useSSL=false";   
	        String userName = "root";
	        String pass = "Hemanth->25012005";
			Connection con = DriverManager.getConnection(url, userName, pass);
			
			String sql = "SELECT *FROM USERS WHERE E_MAIL = ? AND PASSWORD = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, e_mail);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
			{
				session.setAttribute("name", rs.getString("name"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			}
			else
			{
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			
			dispatcher.forward(request, response);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
