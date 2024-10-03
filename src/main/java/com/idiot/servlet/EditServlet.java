package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
   private static final String query = "UPDATE BOOK SET BOOKNAME=?,BOOKEDITION=?,BOOKPRICE=? WHERE id=?;" ;
	
    @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
   	PrintWriter pw = res.getWriter();
   	
   	res.setContentType("text/html");
   	
   	int id = Integer.parseInt(req.getParameter("id"));
   	String bookName = req.getParameter("bookName");
   	String bookEdition = req.getParameter("bookEdition");
   	float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
   	
   	
   	try {
   		Class.forName("com.mysql.cj.jdbc.Driver");
   	} catch(ClassNotFoundException cnf) {
   		cnf.printStackTrace();    	
   	}
   	try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/databook", "root","Pratik@321");
   		PreparedStatement ps = con.prepareStatement(query);){
   		ps.setString(1, bookName);
   		ps.setString(2, bookEdition);
   		ps.setFloat(3, bookPrice);
   		ps.setInt(4, id);
   		int count = ps.executeUpdate();
   		if(count == 1) {
   			pw.println("<h2>Record Is Edited Sucessfully");
   		} else {
   			pw.println("<h2>Record Is NOT Edited. PLease Try Again");
   		}
   	} catch(SQLException se) {
   		se.printStackTrace();
   		pw.println("<h2>"+ se.getMessage() + "</h2>");
   	} catch (Exception e) {
   		e.printStackTrace();
   		pw.println("<h1>"+ e.getMessage() + "</h1>");
   	}
   	pw.println("<br>");
   	pw.println("<a href='home.html'>Home</a>");
   	pw.println("<br>");
	pw.println("<a href='bookList'>Book List</a>");
   	
   }
    @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
   	doGet(req, res);
   }
}
