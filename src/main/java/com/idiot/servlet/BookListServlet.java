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

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
	private static final String query = "SELECT ID,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOK;" ;
	
	
    @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
   	PrintWriter pw = res.getWriter();
   	
   	res.setContentType("text/html");
   	
   	try {
   		Class.forName("com.mysql.cj.jdbc.Driver");
   	} catch(ClassNotFoundException cnf) {
   		cnf.printStackTrace();    	
   	}
   	try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/databook", "root","Pratik@321");
   		PreparedStatement ps = con.prepareStatement(query);){
   		ResultSet rs = ps.executeQuery();
   		pw.println("<table border='1' align='center'>");
   		pw.println("<tr>");
   		pw.println("<th>Book ID</th>");
   		pw.println("<th>Book Name</th>");
   		pw.println("<th>Book Edition</th>");
   		pw.println("<th>Book Price</th>");
   		pw.println("<th>Edit Book</th>");
   		pw.println("<th>Delete Book</th>");
   		pw.println("</tr>");
   		while(rs.next()) {
   			pw.println("<tr>");
   			pw.println("<td>"+rs.getInt(1)+"</td>");
   			pw.println("<td>"+rs.getString(2)+"</td>");
   			pw.println("<td>"+rs.getString(3)+"</td>");
   			pw.println("<td>"+rs.getFloat(4)+"</td>");
   			pw.println("<td><a href='editBook?id="+rs.getInt(1)+"'>Edit</a></td>");
   			pw.println("<td><a href='deleteBook?id="+rs.getInt(1)+"'>Delete</a></td>");
   	   	    pw.println("</tr>");
   		}
   		pw.println("</table>");
   		
   	} catch(SQLException se) {
   		se.printStackTrace();
   		pw.println("<h2>"+ se.getMessage() + "</h2>");
   	} catch (Exception e) {
   		e.printStackTrace();
   		pw.println("<h1>"+ e.getMessage() + "</h1>");
   	}
   	pw.println("<a href='home.html'>Home</a>");
   }
    @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
   	doGet(req, res);
   }
}
