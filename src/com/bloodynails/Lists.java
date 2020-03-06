package com.bloodynails;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Lists
 */
@WebServlet("/Lists")
public class Lists extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Lists() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		//request.getRequestDispatcher("/Lists.jsp").forward(request, response);
		
		ArrayList<List> lists = getLists();
		PrintWriter out = response.getWriter();
		try {
			// TODO: load an jsp file instead of printing this
			// TODO: print all lists at the right position html
			out.println("<h3>"+lists.get(0).getDescription()+", "+lists.get(0).getLang1()+", "+lists.get(0).getLang2()+"</h3>");
			out.println("<h3>"+lists.get(1).getDescription()+", "+lists.get(1).getLang1()+", "+lists.get(1).getLang2()+"</h3>");
			DBManager.save(lists.get(0));
			DBManager.save(lists.get(1));
			Word w = new Word(lists.get(0).getID(), "дерево", "Baum");
			Word w2 = new Word(lists.get(0).getID(), "де́душка", "Opa");
			DBManager.save(w);
			DBManager.save(w2);
			LinkedHashMap<String, String> words = DBManager.getWordsByListID(lists.get(0).getID());
			for(Map.Entry<String, String> entry : words.entrySet()) {
				out.println("<h3>"+entry.getKey()+", "+entry.getValue()+"</h3>");
			}
		}
		finally {
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private static ArrayList<List> getLists() {
		ArrayList<List> lists = new ArrayList<List>();
		lists.add(new List("Familie", "Russisch", "Deutsch"));
		lists.add(new List("Heirat", "Russisch", "Deutsch"));
		return lists;
	}

}
