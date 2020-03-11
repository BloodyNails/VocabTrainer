package com.bloodynails;

import java.io.IOException;
import java.util.LinkedHashMap;
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
		
		LinkedList<VocabList> lists = DBManager.getAllLists();
		int listCount = lists.size();
		
		request.setAttribute("listCount", listCount);
		
		/*
		for(int i = 0; i < listCount; i++) {
			Long id = (Long) lists.get(i).getID();
			String description = lists.get(i).getDescription();
			String lang1 = lists.get(i).getLang1();
			String lang2 = lists.get(i).getLang2();
			request.setAttribute("description", description);
			request.setAttribute("lang1", lang1);
			request.setAttribute("lang2", lang2);
			System.out.println(id+", "+description+", "+lang1+", "+lang2);
		}
		*/
		
		request.setAttribute("lists", lists);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.getRequestDispatcher("/Lists.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
