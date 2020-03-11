package com.bloodynails;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListDetailView
 */
@WebServlet("/Lists/View")
public class ListDetailView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ListDetailView() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		VocabList list = DBManager.getListByID(id);
		System.out.println("list: "+list.getID()+", "+list.getDescription()+", "+list.getLang1()+", "+list.getLang2());
		request.getRequestDispatcher("/ListDetailView.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
