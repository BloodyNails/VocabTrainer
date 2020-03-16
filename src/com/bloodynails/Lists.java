package com.bloodynails;

import java.io.IOException;
import java.util.LinkedList;

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
	
    public Lists() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LinkedList<VocabList> lists = DBManager.getAllLists();
		int listCount = lists.size();
		
		request.setAttribute("listCount", listCount);		
		request.setAttribute("lists", lists);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.getRequestDispatcher("/Lists.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// DELETE LIST
		
		if(request.getParameter("listID") != null) {
			Long listID = Long.parseLong(request.getParameter("listID"));
			if(listID != null && listID > -1) {
				DBManager.deleteListByID(listID);
			}
		}
		
		if(request.getParameter("description") != null && request.getParameter("lang1") != null && request.getParameter("lang2") != null) {
			String description = (String) request.getParameter("description");
			String lang1 = (String) request.getParameter("lang1");
			String lang2 = (String) request.getParameter("lang2");
			if(description != null && lang1 != null && lang2 != null) {
				if(!description.isEmpty() && !lang1.isEmpty() && !lang2.isEmpty()) {
					DBManager.save(new VocabList(description, lang1, lang2));
				}
			}
		}
		
		
		doGet(request, response);
	}

}
